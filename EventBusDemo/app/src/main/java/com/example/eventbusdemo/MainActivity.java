package com.example.eventbusdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.eventbusdemo.eventbus.EventBean;
import com.example.eventbusdemo.eventbus.EventBus;
import com.example.eventbusdemo.eventbus.Subscrbile;
import com.example.eventbusdemo.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
//        findViewById(R.id.actionSe
//        cond).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
//            }
//        });

    }

    /**
     * 获取EventBus数据
     */
    @Subscrbile(threadMode = ThreadMode.MAIN)
    private void getMsg(EventBean bean){
        Log.e("EventBus==>",bean.toString());
    }
}
