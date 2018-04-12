package com.ljt.androidplugin.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by 10006465 on 2018/4/8.
 */

public class MyInstrumentation extends Instrumentation {
    private static final String TAG = "MyInstrumentation";

    Instrumentation mBase;

    public MyInstrumentation(Instrumentation mBase) {
        this.mBase = mBase;
    }

    public ActivityResult execStartActivity(Context who,IBinder contextThread,IBinder token,
                                            Activity target,Intent intent,int requestCode,Bundle options){
        Log.d(TAG, "\n执行了startActivity, 参数如下: \n" + "who = [" + who + "], " +
                "\ncontextThread = [" + contextThread + "], \ntoken = [" + token + "], " +
                "\ntarget = [" + target + "], \nintent = [" + intent +
                "], \nrequestCode = [" + requestCode + "], \noptions = [" + options + "]");
        try {
            Method execStartActivity = Instrumentation.class.getDeclaredMethod("execStartActivity",
                    Context.class,IBinder.class,IBinder.class,
                    Activity.class,Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            //返回调这个方法的类
            return (ActivityResult) execStartActivity.invoke(mBase,who,contextThread,token,target,intent,requestCode,options);
        }catch (Exception e){
            throw new RuntimeException("do not support!!! pls adapt it");
        }

    }


}





















