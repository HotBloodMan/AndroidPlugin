package com.ljt.androidplugin.interceptactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ljt.androidplugin.R;
/*要注意的是,这个Activity并没有再Manifest中注册!!!
* */
public class InterceptActivity extends AppCompatActivity {

    private static final String TAG = "InterceptActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_intercept);
        Button button = new Button(this);
        button.setText("启动TargetActivity");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动目标Activity; 注意这个Activity是没有在AndroidManifest.xml中显式声明的
                // 但是调用者并不需要知道, 就像一个普通的Activity一样
                Log.d(TAG,"-------->>>InterceptActivity begin");
                startActivity(new Intent(InterceptActivity.this, TargetActivity.class));
                Log.d(TAG,"-------->>>InterceptActivity over");
            }
        });
        setContentView(button);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            AMSHookHelper.hookActivityManagerNative();
            AMSHookHelper.hookActivityThreadHandler();
        }catch (Throwable throwable){
            throw new RuntimeException("hook failed", throwable);
        }
    }
}



















