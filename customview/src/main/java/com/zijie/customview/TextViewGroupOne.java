package com.zijie.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hezijie on 2019/6/26.
 */

public class TextViewGroupOne extends ViewGroup {
    private int offset = 0;

    public TextViewGroupOne(Context context) {
        super(context);
    }

    public TextViewGroupOne(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewGroupOne(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**child.getLayoutParams(),childAt.getMeasuredWidth(),child.getWidth();
     * 测量完成之前使用child.getLayoutParams(),这个是子view向父布局请求的宽度，它的值可能是MATCH或WRAP，那么获取的就是相应的-1，-2。
     * childAt.getMeasuredWidth()这个是测量完成之后子view的实际高度，单位px
     *  child.getWidth()测量并且onLayout()执行完成之后子view实际大小单位px。
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //传递进入父布局的宽度特征，padding，通过子View的LayoutParams获取到的子View的宽或高
            int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, 0, child.getLayoutParams().width);
            int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, child.getLayoutParams().height);
            //测量子view的宽高
            child.measure(childWidthSpec, childHeightSpec);
        }
        int parWidth = 0;
        int parHeight = 0;
        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                for (int i = 0; i < childCount; i++) {
                    View childAt = getChildAt(i);
                    //子view以及测量完成，所以可以使用测量的宽度
                    int childWidth = i * offset + childAt.getMeasuredWidth();
                    parWidth = Math.max(parWidth,childWidth);
                }
                break;
            case MeasureSpec.EXACTLY: //精确模式
                parWidth = width;
                break;
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < childCount; i++) {
                    View childAt = getChildAt(i);
                    //子view以及测量完成，所以可以使用测量的宽度
                    int childWidth = i * offset + childAt.getMeasuredWidth();
                    parWidth = Math.max(parWidth,childWidth);
                }
                break;
        }

        switch (heightMode){
            case MeasureSpec.AT_MOST:
                for (int i = 0; i < childCount; i++) {
                    View childAt = getChildAt(i);
                    parHeight += childAt.getMeasuredHeight();
                }
                break;
            case MeasureSpec.EXACTLY: //精确模式
                parHeight = height;
                break;
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < childCount; i++) {
                    View childAt = getChildAt(i);
                    parHeight += childAt.getMeasuredHeight();
                }
                break;
        }
        //接下来需要再次测量父布局的尺寸，因为父布局会受到子view的影响
        //这里直接确定父布局的宽度和高度，不需要测量模式，因为子view测量完成后父布局的大小就确定了
        setMeasuredDimension(parWidth,parHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0 ;
        int right = 0;
        int top = 0;
        int bottom = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            left = offset * i;
            right = left + childAt.getMeasuredWidth();
            bottom = top + childAt.getMeasuredHeight();
            childAt.layout(left,top,right,bottom);
            //不考虑margin和padding gravity,top是前一个子view的底部。
            top += childAt.getMeasuredHeight();
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return super.generateLayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return super.generateLayoutParams(attrs);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams{

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.TextViewGroupOne_Layout);
            int simpleAttr = typedArray.getInteger(R.styleable.TextViewGroupOne_Layout_layout_simple_attr, 0);
            int gravity = typedArray.getInteger(R.styleable.TextViewGroupOne_Layout_android_layout_gravity, -1);
            typedArray.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }


    }
}
