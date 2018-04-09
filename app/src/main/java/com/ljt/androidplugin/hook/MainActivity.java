package com.ljt.androidplugin.hook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ljt.androidplugin.hook.HookHelper;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        Button btn = new Button(this);
        btn.setText("测试界面");
        setContentView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setData(Uri.parse("http://www.baidu.com"));
                getApplicationContext().startActivity(intent);
                Log.d(TAG,"------>>>> getApplicationContext()");
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        Log.d(TAG,"------>>>> attachBaseContext()");
        try {
            //在这里进行Hook
            HookHelper.attachContext();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
