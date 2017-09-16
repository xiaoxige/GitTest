package cn.xiaoxige.refreshtest;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

/**
 * Created by ChaiHongwei on 2016/9/7 15:50.
 * 滑动列表,显示或隐藏标题栏
 */
public class StickyNavLayout extends LinearLayout implements NestedScrollingParent {
    private static final String TAG = StickyNavLayout.class.getSimpleName();

    private View mTopView;
    private View mBttomView;
    // 引起滑动的布局（用于判断是否是同一个布局引起的滑动）
    private View mCurrentlyTargetView;
    // 正真滚动的布局
    private View mRealScrollView;
    private int mTopViewHeight;
    private int mRootViewHeight;
    private int mMaxScrollY;
    private OverScroller mScroller;



    public StickyNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        mScroller = new OverScroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTopView = findViewById(R.id.topView);
        mBttomView = findViewById(R.id.bottomView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //不限制顶部的高度
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        mTopViewHeight = getChildAt(0).getMeasuredHeight();

        ViewGroup.LayoutParams params = mBttomView.getLayoutParams();
        mRootViewHeight = getMeasuredHeight();
        params.height = mRootViewHeight;
        mBttomView.setLayoutParams(params);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTopView.getMeasuredHeight();
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return ((nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        // 当引起滑动的布局为空或者引起滑动的布局改变的时候， 给真正滑动的布局进行赋值
        if (mCurrentlyTargetView == null
                || !target.equals(mCurrentlyTargetView)
                ) {
            mCurrentlyTargetView = target;
            mRealScrollView = mCurrentlyTargetView.findViewById(R.id.recyclerView);
        }

    }

    @Override
    public void onStopNestedScroll(View target) {
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }


    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

        int mRealScrollHeight = mRealScrollView.getMeasuredHeight();
        // mRealScrollHeight + mTopViewHeight - mRootViewHeight 可以准确的可滑动的滑动距离，
        mMaxScrollY = Math.max(0, Math.min(mRealScrollHeight + mTopViewHeight - mRootViewHeight, mTopViewHeight));

        boolean hiddenTop = dy > 0 && getScrollY() < mMaxScrollY;
        boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(mRealScrollView, -1);

        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.e(TAG, "onNestedFling");


        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        //down - //up+
        if (getScrollY() >= mMaxScrollY || getScrollY() == 0) {
            return false;
        }

        fling((int) velocityY);
        return true;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }

    private void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mMaxScrollY);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mMaxScrollY) {
            y = mMaxScrollY;
        }

        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

}
