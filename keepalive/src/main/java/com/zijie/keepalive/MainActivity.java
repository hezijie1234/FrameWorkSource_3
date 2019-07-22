package com.zijie.keepalive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zijie.keepalive.account.AccountHelper;
import com.zijie.keepalive.jobscheduler.MyJobService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        KeepManager.getmInstance().registerKeepBroad(this);
//        startService(new Intent(this,ForegroundService.class));
//        startService(new Intent(this,StickyService.class));
//
//        AccountHelper.addAccount(this);
//        AccountHelper.autoSync();

        //限制比较大，很多手机厂商做了限制无法使用
        MyJobService.startJob(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeepManager.getmInstance().unRegisterKeepBroad(this);
    }
}
