package com.ljt.androidplugin.testmakejar;

import android.app.Activity;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ljt.androidplugin.R;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MakeJarActivity extends Activity {
    private static final String TAG = "MakeJarActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_jar);
        findViewById(R.id.btn_makejar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJar(v);
            }
        });
    }

    private void startJar(View v) {
        Log.d(TAG,"----------->>>dexPath= begin");
        final File dexOutPutDir = getDir("dex", 0);
        String dexPath = Environment.getExternalStorageDirectory().toString() + File.separator + "new_mysdk.jar";
        Log.d(TAG,"----------->>>dexPath= "+dexPath.toString());
        DexClassLoader dexClassLoader = new DexClassLoader(dexPath, dexOutPutDir.getAbsolutePath(), null, getClassLoader());
        try {
            Class<?> loadClazz = dexClassLoader.loadClass("com.example.testlogplug.LogUtils");
            Object o = loadClazz.newInstance();
            Method printlog = loadClazz.getDeclaredMethod("printLog");
            printlog.setAccessible(true);
            printlog.invoke(o);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d(TAG,"----------->>>dexPath= over");
    }

}
