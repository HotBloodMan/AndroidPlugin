package com.ljt.androidplugin.interceptactivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;

import java.lang.reflect.Field;

/**
 * Created by 10006465 on 2018/4/10.
 */

public class ActivityThreadHandlerCallback implements Handler.Callback{

    Handler mBase;

    public ActivityThreadHandlerCallback(Handler mBase) {
        this.mBase = mBase;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            // ActivityThread里面 "LAUNCH_ACTIVITY" 这个字段的值是100
            // 本来使用反射的方式获取最好, 这里为了简便直接使用硬编码
            case 100:
                handleLaunchActivity(msg);
                break;
        }
        mBase.handleMessage(msg);
        return true;
    }
    private void handleLaunchActivity(Message msg){
        Object obj = msg.obj;
        try {
            // 根据源码:
            // 这个对象是 ActivityClientRecord 类型
            // 我们修改它的intent字段为我们原来保存的即可.
            // switch (msg.what) {
            //      case LAUNCH_ACTIVITY: {
            //          Trace.traceBegin(Trace.TRACE_TAG_ACTIVITY_MANAGER, "activityStart");
            //          final ActivityClientRecord r = (ActivityClientRecord) msg.obj;

            //          r.packageInfo = getPackageInfoNoCheck(
            //                  r.activityInfo.applicationInfo, r.compatInfo);
            //         handleLaunchActivity(r, null);
            Field intent = obj.getClass().getDeclaredField("intent");
            intent.setAccessible(true);
            Intent raw= (Intent) intent.get(obj);
            Intent target = raw.getParcelableExtra(AMSHookHelper.EXTRA_TARGET_INTENT);
            raw.setComponent(target.getComponent());
        }catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}















