package com.ljt.androidplugin.binder;

import android.content.ClipData;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by 10006465 on 2018/4/9.
 */

public class BinderHookHandler implements InvocationHandler{
    private static final String TAG ="BinderHookHandler";
    Object base;


    public BinderHookHandler(IBinder base,Class<?> stubClass){
        try {
            Method asInterfaceMethod = stubClass.getDeclaredMethod("asInterface", IBinder.class);
            this.base=asInterfaceMethod.invoke(null,base);
        }catch(Exception e){
            throw new RuntimeException("hooked failed");
        }
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        if("getPrimaryClip".equals(method.getName())){
            Log.d(TAG, "---->>hook getPrimaryClip");
            return ClipData.newPlainText(null,"you are hooked");
        }
        if("hasPrimaryClip".equals(method.getName())){
            return true;
        }
        return method.invoke(base,args);
    }
}
