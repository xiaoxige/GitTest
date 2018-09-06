package com.saintfine.customer.test.xiaoxigetest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
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

    private OnProgressListener mProgressListener;

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

    /**
     * 改方法会有好多重复的代码， 不在合并了， 感觉这样好理解点
     *
     * @param dx
     * @param dy
     * @param consumed
     * @param offsetInWindow
     * @return
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow) {
//        if (!isUpTelescopic()) {
//            if (dy <= mMoveY) {
//                mMoveY += dy;
//                consumed[1] = dy;
//                requestLayout();
//            }
//
//        }

        // 是否可以伸缩
        int beforeMoveY = mMoveY;
        if (Math.abs(dy) > 0 && isTelescopic()) {
            if (dy > 0) {
                // 上 +
                int tempResult = mMoveY + dy;
                mMoveY = Math.min(tempResult, mFirstViewHeight);
//                dy = tempResult - mMoveY;
//                consumed[1] = dy == beforeDy ? 0 : dy;
                consumed[1] = mMoveY - beforeMoveY;
            } else {
                // 下 -
                // 转正
                dy = Math.abs(dy);
                int tempResult = mMoveY - dy;
                mMoveY = Math.max(tempResult, 0);
//                dy = mMoveY - tempResult;
                // 还原
//                dy = -dy;
//                consumed[1] = dy == beforeDy ? 0 : dy;
                consumed[1] = mMoveY - beforeMoveY;
            }
        }

        // 分发进度
        dispatchTransparency();

        if (beforeMoveY != mMoveY) {
            requestLayout();
        }

//        return this.mScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, null);

        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    private void dispatchTransparency() {
        float progress = ((float) mMoveY) / mFirstViewHeight;
        mFirstView.setAlpha(1 - progress);
        if (mProgressListener != null) {
            mProgressListener.progressChanged(this, progress);
        }
    }

    /**
     * 是否可伸缩
     *
     * @return
     */
    private boolean isTelescopic() {
//        if (dy > 0) {
//            // 上 +
//            return mMoveY < mFirstViewHeight;
//        } else {
//            // 下 -
//            return mMoveY > 0;
//        }
        return mMoveY <= mFirstViewHeight && mMoveY >= 0;
    }

    public void setProgressListener(OnProgressListener progressListener) {
        this.mProgressListener = progressListener;
    }

    public interface OnProgressListener {
        void progressChanged(View v, float progress);
    }

}
