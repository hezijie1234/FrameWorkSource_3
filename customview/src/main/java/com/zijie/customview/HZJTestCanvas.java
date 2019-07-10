package com.zijie.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hezijie on 2019/7/5.
 */

public class HZJTestCanvas extends View {
    private Paint mPaint;
    public HZJTestCanvas(Context context) {
        this(context,null);
    }

    public HZJTestCanvas(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HZJTestCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        record();
    }
    private Picture picture = new Picture();
    public void record(){
        Canvas canvas = picture.beginRecording(getWidth(), getHeight());
        canvas.translate(100,100);
        canvas.drawColor(Color.GRAY);
        canvas.drawPoints(new float[]{1,1,100,100},mPaint);
        canvas.drawRect(new RectF(101,101,200,200),mPaint);
        canvas.drawRoundRect(new RectF(200,250,350,500),30,60,mPaint);
        canvas.drawOval(new RectF(200,550,350,650),mPaint);
        canvas.drawCircle(500,500,400,mPaint);
        picture.endRecording();
    }

    public void playPicture(){
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPicture(picture,new RectF(0,0,getWidth(),getTop()));
    }
}
