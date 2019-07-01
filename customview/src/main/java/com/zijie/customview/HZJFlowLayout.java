package com.zijie.customview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
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

    public HZJFlowLayout(Context context) {
        super(context);
    }

    public HZJFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HZJFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HZJFlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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

        //这里需要重新测量父布局的宽度和高度
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? parentWidth : width,heightMode == MeasureSpec.EXACTLY ? parentHeight : height);
    }

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
