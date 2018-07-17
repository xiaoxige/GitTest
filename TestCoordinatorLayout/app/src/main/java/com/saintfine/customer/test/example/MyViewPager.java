package com.saintfine.customer.test.example;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * @author by zhuxiaoan on 2018/7/16 0016.
 */

public class MyViewPager extends ViewPager {

    private int mTouchSlop;

    public MyViewPager(Context context) {
        this(context, null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private float startX;
    private float startY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = getX();
                startY = getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float disX = Math.abs(getX() - startX);
                float disY = Math.abs(getY() - startY);
                if (disX <= mTouchSlop && disY <= mTouchSlop) {
                    break;
                }
                getParent().requestDisallowInterceptTouchEvent(disY > disX);
                startX = getX();
                startY = getY();
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
