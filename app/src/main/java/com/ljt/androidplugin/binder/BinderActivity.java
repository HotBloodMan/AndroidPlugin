package com.ljt.androidplugin.binder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.ljt.androidplugin.R;

public class BinderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{

            BinderHookHelper.hookClipboardService();
            Log.d("BinderActivity","------>>>");
        }catch (Exception e){
            e.printStackTrace();
        }
        EditText editText = new EditText(this);
        setContentView(editText);
    }
}
