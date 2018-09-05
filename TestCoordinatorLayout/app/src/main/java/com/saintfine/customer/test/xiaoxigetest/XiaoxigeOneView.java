package com.saintfine.customer.test.xiaoxigetest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class XiaoxigeOneView extends ViewGroup implements NestedScrollingChild {

    private Context mContext;

    private View mFirstView;
    private View mSecondView;

    private int mRootWidth;
    private int mFirstViewHeight;
    private int mSecondViewHeight;
    private int mMaxRootViewHeight;
    private int mMoveY;

    private NestedScrollingChildHelper mScrollingChildHelper;

    public XiaoxigeOneView(@NonNull Context context) {
        this(context, null);
    }

    public XiaoxigeOneView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XiaoxigeOneView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.mScrollingChildHelper = new NestedScrollingChildHelper(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFirstView = getChildAt(0);
        mSecondView = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mFirstView != null && mFirstViewHeight <= 0) {
            measureChild(mFirstView, 0, 0);
            mFirstViewHeight = mFirstView.getMeasuredHeight();
        }

        if (mSecondView != null && mSecondViewHeight <= 0) {
            measureChild(mSecondView, 0, 0);
            mSecondViewHeight = mSecondView.getMeasuredHeight();
        }

        mRootWidth = getMeasuredWidth();
        mMaxRootViewHeight = mFirstViewHeight + mSecondViewHeight;

        setMeasuredDimension(widthMeasureSpec, mMaxRootViewHeight - mMoveY);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mFirstView.layout(0, 0, mRootWidth, mFirstViewHeight);
        mSecondView.layout(0, mFirstViewHeight - mMoveY, mRootWidth, mMaxRootViewHeight - mMoveY);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow) {
        if (!isUpTelescopic()) {
            if (dy <= mMoveY) {
                mMoveY += dy;
                consumed[1] = dy;
                requestLayout();
            }

        }
        return this.mScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, null);
    }

    private boolean isUpTelescopic() {
        return mMoveY >= mFirstViewHeight;
    }

}
