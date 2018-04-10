package com.ljt.androidplugin.amspms;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by 10006465 on 2018/4/9.
 */

public class HookHelper {
    private static final String TAG ="HookHelper";
    public static void hookActivityManager(){
        try {
            Log.d(TAG,"---->>>>ams begin");
            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);

            Object gDefault = gDefaultField.get(null);
            Class<?> singleton = Class.forName("android.util.Singleton");
            Field mInstanceField = singleton.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            Object rawIActivityManager = mInstanceField.get(gDefault);
            Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{iActivityManagerInterface},
                    new HookHandler(rawIActivityManager));
               mInstanceField.set(gDefault,proxy);
            Log.d(TAG,"---->>>>ams over");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void hookPackageManager(Context context) {
        try {
            Log.d(TAG,"---->>>>pms begin");
            // 获取全局的ActivityThread对象
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            // 获取ActivityThread里面原始的 sPackageManager
            Field sPackageManagerField = activityThreadClass.getDeclaredField("sPackageManager");
            sPackageManagerField.setAccessible(true);
            Object sPackageManager = sPackageManagerField.get(currentActivityThread);

            // 准备好代理对象, 用来替换原始的对象
            Class<?> iPackageManagerInterface = Class.forName("android.content.pm.IPackageManager");
            Object proxy = Proxy.newProxyInstance(iPackageManagerInterface.getClassLoader(),
                    new Class<?>[] { iPackageManagerInterface },
                    new HookHandler(sPackageManager));

            // 1. 替换掉ActivityThread里面的 sPackageManager 字段
            sPackageManagerField.set(currentActivityThread, proxy);

            // 2. 替换 ApplicationPackageManager里面的 mPm对象
            PackageManager pm = context.getPackageManager();
            Field mPmField = pm.getClass().getDeclaredField("mPM");
            mPmField.setAccessible(true);
            mPmField.set(pm, proxy);
            Log.d(TAG,"---->>>>pms over");
        } catch (Exception e) {
            throw new RuntimeException("hook failed", e);
        }
    }
}
