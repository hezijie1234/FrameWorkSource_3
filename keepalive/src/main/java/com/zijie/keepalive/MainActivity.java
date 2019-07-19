package com.zijie.keepalive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KeepManager.getmInstance().registerKeepBroad(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeepManager.getmInstance().unRegisterKeepBroad(this);
    }
}
