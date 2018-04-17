package com.example.testlogplug;

import android.util.Log;

/**
 * Created by 10006465 on 2018/4/17.
 */

public class LogUtils {
    public static final String TAG="LogUtils";
    private void  printLog(){
        Log.e(TAG,"这是来自另外一个dex中的log");
    }
}
