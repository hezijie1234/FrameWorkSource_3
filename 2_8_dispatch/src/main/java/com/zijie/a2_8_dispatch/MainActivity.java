package com.zijie.a2_8_dispatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String TAG = "111";
    private int[] iv = new int[]{R.mipmap.iv_0, R.mipmap.iv_1, R.mipmap.iv_2,
            R.mipmap.iv_3, R.mipmap.iv_4, R.mipmap.iv_5,
            R.mipmap.iv_6, R.mipmap.iv_7, R.mipmap.iv_8};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        findViewById(R.id.btn_scroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ScrollActivity.class));
            }
        });
        BadViewPager pager = findViewById(R.id.viewpager);

        List<Map<String, Integer>> strings = new ArrayList<>();

        Map<String, Integer> map;

        for (int i = 0; i < 20; i++) {
            map = new HashMap<>();
            map.put("key", iv[i % 9]);
            strings.add(map);
        }

        MyPagerAdapter adapter = new MyPagerAdapter(this, strings);
        pager.setAdapter(adapter);
    }

    public void scrollDispatchClick(View view) {
        startActivity(new Intent(this,ScrollDispatchActivity.class));
    }
}
