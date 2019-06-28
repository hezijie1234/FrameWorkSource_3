package com.zijie.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testBaseView1(View view) {
        startActivity(new Intent(this,TestViewActivity1.class));
    }

    public void testBaseView2(View view) {
        startActivity(new Intent(this,TestViewActivity2.class));
    }

    public void testScollby(View view) {
        startActivity(new Intent(this,TestScollByActivity.class));
    }
}
