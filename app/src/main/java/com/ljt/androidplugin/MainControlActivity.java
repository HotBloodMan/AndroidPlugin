package com.ljt.androidplugin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ljt.androidplugin.amspms.AmsPmsActivity;
import com.ljt.androidplugin.binder.BinderActivity;
import com.ljt.androidplugin.hook.MainActivity;
import com.ljt.androidplugin.interceptactivity.InterceptActivity;
import com.ljt.androidplugin.testmakejar.MakeJarActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainControlActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.btn_control_1)
    Button btnHook;
    @InjectView(R.id.btn_control_2)
    Button btnHookBind;
    @InjectView(R.id.btn_control_3)
    Button btnHookAmsPms;
    @InjectView(R.id.btn_control_4)
    Button btnInterceptActivity;
    @InjectView(R.id.btn_control_5)
    Button btnMakeJar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_control);
        ButterKnife.inject(this);
        btnHook.setOnClickListener(this);
        btnHookBind.setOnClickListener(this);
        btnHookAmsPms.setOnClickListener(this);
        btnInterceptActivity.setOnClickListener(this);
        btnMakeJar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_control_1:
                startActivitys(MainActivity.class);
                break;
            case R.id.btn_control_2:
                startActivitys(BinderActivity.class);
                break;
            case R.id.btn_control_3:
                startActivitys(AmsPmsActivity.class);
                break;
            case R.id.btn_control_4:
                startActivitys(InterceptActivity.class);
                break;
            case R.id.btn_control_5:
                startActivitys(MakeJarActivity.class);
                break;
        }
    }
    public void startActivitys(Class clazz){
        Intent intent = new Intent(MainControlActivity.this, clazz);
        startActivity(intent);
    }
}
