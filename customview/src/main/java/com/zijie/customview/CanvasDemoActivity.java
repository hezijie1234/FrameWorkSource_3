package com.zijie.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CanvasDemoActivity extends AppCompatActivity {
    private HZJTestCanvas hzjTestCanvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_demo);
        hzjTestCanvas = findViewById(R.id.hzj_canvas);
    }

    public void recordPicture(View view) {
        hzjTestCanvas.record();
    }

    public void playPicture(View view) {
        hzjTestCanvas.playPicture();
    }
}
