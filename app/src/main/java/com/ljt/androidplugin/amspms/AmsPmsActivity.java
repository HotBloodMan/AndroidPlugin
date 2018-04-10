package com.ljt.androidplugin.amspms;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ljt.androidplugin.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AmsPmsActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.btn_ams)
    Button btnAms;

    @InjectView(R.id.btn_pms)
    Button btnPms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ams_pms);
        ButterKnife.inject(this);
        btnAms.setOnClickListener(this);
        btnPms.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ams:
                // 测试AMS HOOK (调用其相关方法)
                Uri uri = Uri.parse("http://wwww.baidu.com");
                Intent t = new Intent(Intent.ACTION_VIEW);
                t.setData(uri);
                startActivity(t);
                break;
            case R.id.btn_pms:
                getPackageManager().getInstalledApplications(0);
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        HookHelper.hookActivityManager();
        HookHelper.hookPackageManager(newBase);
        super.attachBaseContext(newBase);
    }
}
