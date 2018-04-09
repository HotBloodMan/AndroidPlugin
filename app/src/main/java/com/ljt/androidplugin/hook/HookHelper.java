package com.ljt.androidplugin.hook;

import android.app.Instrumentation;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by 10006465 on 2018/4/8.
 */

public class HookHelper {
    private static final String TAG = "HookHelper";
    public static void attachContext()throws Exception{
        Log.d(TAG,"--------->>>>attachContext");
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);

        Object currentActivityThread=currentActivityThreadMethod.invoke(null);
        Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation mInstrumentation= (Instrumentation) mInstrumentationField.get(currentActivityThread);
      // 创建代理对象
        Instrumentation myInstrumentation = new MyInstrumentation(mInstrumentation);
        // 偷梁换柱
       mInstrumentationField.set(currentActivityThread,myInstrumentation);
    }
}
