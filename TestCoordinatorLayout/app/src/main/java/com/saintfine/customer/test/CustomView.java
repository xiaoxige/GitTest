package com.saintfine.customer.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * @author by zhuxiaoan on 2018/7/13 0013.
 */

public class CustomView extends LinearLayout {

    private ViewDragHelper mDragHelper;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragCallback());
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            left = Math.min(getMeasuredWidth() - child.getMeasuredWidth(), Math.max(left, 0));
            ViewHelper.setTranslationX(CustomView.this, left);
            return 0;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            top = Math.min(getMeasuredHeight() - child.getMeasuredHeight(), Math.max(top, 0));
            return top;
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }
}