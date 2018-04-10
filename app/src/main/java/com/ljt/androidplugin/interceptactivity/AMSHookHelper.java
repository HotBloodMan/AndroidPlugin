package com.ljt.androidplugin.interceptactivity;

import android.os.Build;
import android.os.Handler;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * Created by 10006465 on 2018/4/10.
 */

public class AMSHookHelper {
    private static final String TAG = "AMSHookHelper";
    public static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    public static void hookActivityManagerNative()throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, NoSuchFieldException {
        Field gDefaultField=null;
        if(Build.VERSION.SDK_INT>=26){
            Class<?> activityManager = Class.forName("android.app.ActivityManager");
            gDefaultField=activityManager.getDeclaredField("IActivityManagerSingleton");
        }else{
            Class<?> activityMangerNativeClass = Class.forName("android.app.ActivityManagerNative");
            gDefaultField=activityMangerNativeClass.getDeclaredField("gDefault");
        }
        gDefaultField.setAccessible(true);
        gDefaultField.setAccessible(true);

        Object gDefault = gDefaultField.get(null);
        Log.d(TAG,"------------->>>gDefault "+gDefault);
        Class<?> singleton = Class.forName("android.util.Singleton");
        Field mInstanceField = singleton.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);

        Object rawIActivityManager = mInstanceField.get(gDefault);
        Log.d(TAG,"------------->>>rawIActivityManager "+rawIActivityManager);
        Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{iActivityManagerInterface}, new IActivityManagerHandler(rawIActivityManager));
        mInstanceField.set(gDefault,proxy);
    }


    public static void hookActivityThreadHandler() throws Exception{
        // 先获取到当前的ActivityThread对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
        currentActivityThreadField.setAccessible(true);
        Object currentActivityThread = currentActivityThreadField.get(null);

        Field mHField = activityThreadClass.getDeclaredField("mH");
        mHField.setAccessible(true);
        Handler mH = (Handler) mHField.get(currentActivityThread);

        // 设置它的回调, 根据源码:
        // 我们自己给他设置一个回调,就会替代之前的回调;
        //  public void dispatchMessage(Message msg) {
        //            if (msg.callback != null) {
        //                handleCallback(msg);
        //            } else {
        //                if (mCallback != null) {
        //                    if (mCallback.handleMessage(msg)) {
        //                        return;
        //                    }
        //                }
        //                handleMessage(msg);
        //            }
        //        }
        Field mCallbackField = Handler.class.getDeclaredField("mCallback");
        mCallbackField.setAccessible(true);
        mCallbackField.set(mH,new ActivityThreadHandlerCallback(mH));
    }

}




















