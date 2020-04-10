package com.zijie.a2_8_dispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by hezijie on 2020/4/10.
 */
public class DispatchScroll extends ScrollView {

    private boolean isTop = true;
    private boolean isBottom = false;
    public DispatchScroll(Context context) {
        super(context);
    }

    public DispatchScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    private int mLastX, mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (y - mLastY > 0){
                    if (getScrollY() == 0){//滑动到了顶部
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }else {
                    if (getChildAt(0).getMeasuredHeight() <= getScrollY() + getHeight()){//滑动到了底部
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;

        }
        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(ev);
    }
}
