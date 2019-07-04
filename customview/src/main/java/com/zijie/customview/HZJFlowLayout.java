package com.zijie.customview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hezijie on 2019/6/28.
 *
 */

public class HZJFlowLayout extends ViewGroup {

    //一行的view集合
    private List<View> lineViewList;
    //行的集合
    private List<List<View>> lines;

    private List<Integer> lineLength;
    //限制一个最小的距离，只有小于这个距离才能算是滑动
    private int minSlopDistance;
    //流式布局的内容的高度
    private int realHeight;

    public HZJFlowLayout(Context context) {
        this(context,null);
    }

    public HZJFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HZJFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        minSlopDistance = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        //当前的行宽
        int currentWidth = 0;
        //当前的行高
        int currentHeight  = 0;
        //用来保存子view向父布局请求的最大宽度和高度。
        int height = 0;
        int width = 0;
        //每一次测量都需要更新行的数据
        lines = new ArrayList<>();
        lineLength = new ArrayList<>();
        lineViewList = new ArrayList<>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = childAt.getLayoutParams();
            //测量子view
            if (layoutParams.height == LayoutParams.MATCH_PARENT){ //子view的高度参数为march_parent时，将其修改掉先按照wrap_content的高度来计算
                int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, 0, layoutParams.width);
                int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, LayoutParams.WRAP_CONTENT);
                childAt.measure(childWidthSpec,childHeightSpec);
            }else {
                measureChild(childAt,widthMeasureSpec,heightMeasureSpec);
            }

            //如果当前行的宽度+下一个子view的宽度大于父布局的宽度，则需要换行。
            if (currentWidth + childAt.getMeasuredWidth() > parentWidth){
                //在换行之前确定宽度的最大值
                width = Math.max(width,currentWidth);
                //换行之前确定所有行的高度和
                height += currentHeight;
                //在换行之前报错行的最高高度
                lineLength.add(currentHeight);
                //换行时保存这一行的view数据
                lines.add(lineViewList);

                //重新初始化一个容器来存储下一行
                lineViewList = new ArrayList<>();
                //换行了也要给下一行的容器添加这个view
                lineViewList.add(childAt);
                //这时需要重新初始化当前的行高和宽
                if (layoutParams.height != LayoutParams.MATCH_PARENT){
                    currentHeight = childAt.getMeasuredHeight();
                }

                currentWidth = childAt.getMeasuredWidth();
            }else {//不需要换行时
                lineViewList.add(childAt);
                if (layoutParams.height != LayoutParams.MATCH_PARENT){
                    currentHeight = Math.max(currentHeight,childAt.getMeasuredHeight());
                }
                currentWidth += childAt.getMeasuredWidth();
            }
            //处理最后一行数据（通过最后一个view来判断这时最后一行）
            if (i == childCount - 1){
                width = Math.max(width,currentWidth);
                height += currentHeight;
                lineLength.add(currentHeight);
                lines.add(lineViewList);
            }
        }

        //在这里对子view中高度为match_parent的进行重新处理（按照同一行的其他子view的最大高度来计算）
        reMeasureChild(widthMeasureSpec,heightMeasureSpec);

        isCanScorll = height > parentHeight;
        realHeight = height;
        //这里需要重新测量父布局的宽度和高度
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? parentWidth : width,heightMode == MeasureSpec.EXACTLY ? parentHeight : height);
    }

    private boolean isCanScorll = false;
    private float startY = 0; //每次滑动的开始位置
    /**
     * 捕获滑动事件并进行处理，但是有限制的滑动才是被允许的：
     *          1.当内容的高度大于流式布局的高度时需要滑动（这个需要在onMeasure中进行处理）。
     *          2.当滑动到地步或者顶部时，我们需要作出判断，避免滑动过头了。
     *
     * @param event
     * @return
     */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.e(TAG, "onTouchEvent: " + isCanScorll );
//        if (!isCanScorll){
//            return super.onTouchEvent(event);
//        }
//        float currY = event.getY();
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                startY = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float move = startY - currY;
//                int scrollY = getScrollY();
//                move = move + scrollY;
//                if (move < 0){
//                    move = 0;
//                }
//                if (move > realHeight - getMeasuredHeight()){
//                    move = realHeight - getMeasuredHeight();
//                }
//                scrollTo(0, (int) move);
//                startY = currY;
//                break;
//            case MotionEvent.ACTION_UP:
//
//                break;
//        }
//
//        return super.onTouchEvent(event);
//    }

    public static final String TAG = "111";
    private float  mLastX = 0;//上一个滑动事件结束的时候的x值
    private float mLastY = 0; //上一个滑动事件结束的时候的y值
    /**
     * 我们先考虑什么时候需要拦截事件： 1.就是y轴的移动距离大于一个限定的最小值，我们才认为这是一个我们认可的滑动事件（把系统的滑动事件过滤）。
     *                                  2.y轴的移动距离大于x轴的移动距离我们才认为他是一个y轴的滑动
     * @param ev
     * @return
     */
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        float currentX = (int) ev.getX();
//        float currentY = (int) ev.getY();
//        boolean isIntceptor = false;
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                //按钮按下时给接下来的滑动事件赋予一个初始值
//                mLastX = currentX;
//                mLastY = currentY;
//
//                Log.e(TAG, "onInterceptTouchEvent:ACTION_DOWN " );
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float moveX = Math.abs(currentX - mLastX);
//                float moveY = Math.abs(currentY - mLastY);
//                if (moveY > moveX && moveY > minSlopDistance){ //需要拦截事件，
//                    isIntceptor = true;
//                }
//                Log.e(TAG, "onInterceptTouchEvent:ACTION_MOVE " );
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.e(TAG, "onInterceptTouchEvent:ACTION_UP " );
//                break;
//        }
//        //保存滑动完成后的坐标值，给下一个滑动当做开始值使用
//        mLastY = currentY;
//        mLastX = currentX;
//        Log.e(TAG, "onInterceptTouchEvent: " + isIntceptor );
//        return isIntceptor;
//    }

    private void reMeasureChild(int widthMeasureSpec, int heightMeasureSpec) {

        for (int i = 0; i < lines.size(); i++) {
            List<View> views = lines.get(i);
            for (int j = 0; j < views.size(); j++) {
                View view = views.get(j);
                LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams.height == LayoutParams.MATCH_PARENT){
                    int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, 0, layoutParams.width);
                    int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, lineLength.get(i));
                    view.measure(childWidthSpec,childHeightSpec);
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int currentX = 0;
        int currentY = 0;
        for (int i = 0; i < lines.size(); i++) {
            List<View> views = lines.get(i);
            for (int j = 0; j < views.size(); j++) {
                View view = views.get(j);
                int left = currentX;
                int top = currentY;
                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();
                view.layout(left,top,right,bottom);
                currentX += view.getMeasuredWidth();
            }
            currentY += lineLength.get(i);
            currentX = 0;
        }
    }
}
