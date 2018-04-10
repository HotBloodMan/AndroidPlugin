package com.ljt.androidplugin.interceptactivity;

import android.content.ComponentName;
import android.content.Intent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by 10006465 on 2018/4/10.
 */

public class IActivityManagerHandler implements InvocationHandler {

    private static final String TAG = "IActivityManagerHandler";

    Object mBase;

    public IActivityManagerHandler(Object mBase) {
        this.mBase = mBase;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 只拦截这个方法
        // 替换参数, 任你所为;甚至替换原始Activity启动别的Activity偷梁换柱
        if("startActivity".equals(method.getName())){
           Intent raw;
            int index=0;

            for(int i=0;i<args.length;i++){
                if(args[i] instanceof Intent){
                    index=i;
                    break;
                }
            }
            raw= (Intent) args[index];
            Intent newIntent = new Intent();
            String fakePackage="com.ljt.androidplugin";
            ComponentName componentName = new ComponentName(fakePackage, FakeActivity.class.getName());
            newIntent.setComponent(componentName);

            //把我们原始要启动的Activity保存起来
            newIntent.putExtra(AMSHookHelper.EXTRA_TARGET_INTENT,raw);
            //替换掉Intent,达到欺骗AMS的目的
            args[index]=newIntent;
            return method.invoke(mBase,args);
        }
        return method.invoke(mBase,args);
    }
}
















