package cn.xiaoxige.refreshtest;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.OverScroller;


/**
 * Created by ChaiHongwei on 2016/9/7 15:50.
 * 滑动列表,显示或隐藏标题栏
 */
public class RefreshStickyNavLayout extends LinearLayout implements NestedScrollingParent {

    private static final String TAG = RefreshStickyNavLayout.class.getSimpleName();
    private TopLoadingView mTopLoadingView;
    private View mTopView;
    private View mBttomView;
    // 引起滑动的布局（用于判断是否是同一个布局引起的滑动）
    private View mCurrentlyTargetView;
    // 正真滚动的布局
    private View mRealScrollView;

    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private OnRefreshListener mListener;

    private int mTopLoadingViewHeight;
    private int mTopViewHeight;
    private int mRootViewHeight;
    private int mMaxScrollY;
    // 整个头部的高度
    private int mHeadHeight;

    private boolean mIsCanScroll = true;
    private boolean mIsRefresh = false;

    /**
     * 用途：
     * 是否手指离开了屏幕， 主要用于滑动头部时, 在滑动完全停止后再去调用stopScroll().
     * 解决（问题）：
     * 在快速滑动头部时， 有时会一直滑动不了， 是因为滑动了但是在手指离开后， 屏幕还会在滑动， 屏幕滑动又造成了头部复原处于未刷新状态
     */
    private boolean mIsUp = false;

    /**
     * 用途：
     * 用于确定是否是滑动的一个条件
     * 解决（问题）：
     * 如果在onInterceptTouchEvent中去除该值的判断条件， 头部的点击事件就会不起作用， 因为点击事件也变成了滑动了
     */
    private int mTouchSlop;
    private float startY;

    public RefreshStickyNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);


        mScroller = new OverScroller(context);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        // 添加头部的刷新布局
        mTopLoadingView = new TopLoadingView(context);
        addView(mTopLoadingView, 0);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTopView = findViewById(R.id.topView);
        mBttomView = findViewById(R.id.bottomView);

        // 为了第一次刚进入界面后把刷新布局隐藏
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                scrollTo(0, mTopLoadingViewHeight);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //不限制顶部的高度
        mTopLoadingView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        mTopLoadingViewHeight = mTopLoadingView.getMeasuredHeight();

        mTopView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        mTopViewHeight = mTopView.getMeasuredHeight();

        ViewGroup.LayoutParams params = mBttomView.getLayoutParams();
        mRootViewHeight = getMeasuredHeight();
        params.height = mRootViewHeight;
        mBttomView.setLayoutParams(params);

        mHeadHeight = mTopViewHeight + mTopLoadingViewHeight;
        onNestedScrollAccepted(null, mBttomView.findViewById(R.id.recyclerView), 0);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTopView.getMeasuredHeight();
        mHeadHeight = mTopViewHeight + mTopLoadingViewHeight;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean isIntercept = false;
        if (mIsRefresh) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsUp = false;
                isIntercept = false;
                startY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:

                float interceptEndY = event.getY();
                float disY = interceptEndY - startY;

                int scrollY = getScrollY();
                // 说明头部还为完全移除窗体, 说明点击在了头部上面

                isIntercept = mIsCanScroll
                        && scrollY <= mHeadHeight
                        && (event.getRawY() <= mHeadHeight - scrollY)
                        //Math.abs(disY) >= mTouchSlop : 该值一定要加上， 不然头部的点击事件会不起作用
                        && Math.abs(disY) >= mTouchSlop
                ;

                Log.e(TAG, "-----------------  " + scrollY + ", " + disY + ", " + isIntercept);

                startY = event.getY();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsUp = true;
                isIntercept = false;
                break;

        }
        return isIntercept;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsRefresh) {
            return true;
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        mVelocityTracker.addMovement(event);

        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsUp = false;
                startY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float touchEndY = event.getY();
                float disY = touchEndY - startY;

                int scrollY = getScrollY();
                if (mIsCanScroll
                        && scrollY <= mHeadHeight
                        && (event.getRawY() <= mHeadHeight - scrollY)
                        ) {
                    onNestedScrollAccepted(null, mBttomView.findViewById(R.id.recyclerView), 0);
                    scrollBy(0, (int) -disY);
                    Log.e("zhuxiaoan", "滚动驱动头部");
                    startScroll();
                }

                Log.e(TAG, "++++++++++++++++++++++++  " + scrollY + ", " + disY + ", " +
                        (scrollY < mHeadHeight
                                && (event.getRawY() <= mHeadHeight - scrollY)
                        ));
                startY = event.getY();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsUp = true;
                if (!mIsCanScroll) {
                    break;
                }
                mVelocityTracker.computeCurrentVelocity(500);
                float velocity = mVelocityTracker.getYVelocity();

                fling((int) -velocity);

                if (mVelocityTracker != null) {
                    mVelocityTracker.clear();
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;

        }
        return true;
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return ((nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0) && mIsCanScroll;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        // 当引起滑动的布局为空或者引起滑动的布局改变的时候， 给真正滑动的布局进行赋值
        if (mCurrentlyTargetView == null
                || !target.equals(mCurrentlyTargetView)) {
            mCurrentlyTargetView = target;
            mRealScrollView = mCurrentlyTargetView.findViewById(R.id.recyclerView);
        }

        int mRealScrollHeight = mRealScrollView.getMeasuredHeight();
        // mRealScrollHeight + mTopViewHeight - mRootViewHeight 可以准确的可滑动的滑动距离，
        mMaxScrollY = Math.max(0,
                Math.min(mRealScrollHeight + mTopLoadingViewHeight + mTopViewHeight
                        - mRootViewHeight, mTopLoadingViewHeight + mTopViewHeight));
        Log.e("zhuxiaoan", "                             " + mMaxScrollY);
        if (mMaxScrollY < mTopLoadingViewHeight) {
            mMaxScrollY = mTopLoadingViewHeight;
        }
    }

    @Override
    public void onStopNestedScroll(View target) {
        stopScroll();
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }


    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

        int mRealScrollHeight = mRealScrollView.getMeasuredHeight();
        // mRealScrollHeight + mTopViewHeight - mRootViewHeight 可以准确的可滑动的滑动距离，
        mMaxScrollY = Math.max(0,
                Math.min(mRealScrollHeight + mTopLoadingViewHeight + mTopViewHeight
                        - mRootViewHeight, mTopLoadingViewHeight + mTopViewHeight));
        if (mMaxScrollY < mTopLoadingViewHeight) {
            mMaxScrollY = mTopLoadingViewHeight;
        }

        boolean hiddenTop = dy > 0 && getScrollY() < mMaxScrollY;
        boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(mRealScrollView, -1);

        if (hiddenTop || showTop) {
            consumed[1] = dy;
            if (!mIsRefresh) {
                Log.e("zhuxiaoan", "滚动驱动2");
                scrollBy(0, dy);
                // 确保头部还未完全隐藏的时候, 滑动布局进行了滑动（这里默认限定了RecyclerView）
                if (mRealScrollView instanceof RecyclerView) {
                    ((RecyclerView) mRealScrollView).scrollToPosition(0);
                }
            } else {
                return;
            }
        }

        if (hiddenTop || showTop) {

            startScroll();
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
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

        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }

        int scrollY = getScrollY();

        // 在要进行惯性滑动前的位置大于头部刷新的位置， 则不让其在快速滑动时去触发刷新
        int minScrollY = (scrollY > mTopLoadingViewHeight) ? mTopLoadingViewHeight : 0;

        // 当头部出现时， 其速度x0.8
        velocityY = scrollY > mMaxScrollY ? velocityY : (int) (velocityY * 0.8);
        Log.e("fasd", "min " + minScrollY + ", " + velocityY);

        mScroller.fling(0, scrollY, 0, velocityY, 0, 0, minScrollY, mMaxScrollY);

        invalidate();
    }


    @Override
    public void scrollTo(int x, int y) {
        Log.e("zhuxiaoan", "scrollTo:Y=" + y);

        // 保证下拉刷新的时候一定位于（0， 0）处,
        // (问题描述：当滑动时， Y轴已经滑动到了0， 触发了下啦刷新， 但是在出发后又被其他条件调用了scrool()方法, 使其在下啦状态的情况下显示了未刷新的界面.)
        if (mIsRefresh) {
            if (getScrollY() != 0) {
                super.scrollTo(x, 0);
            }
            return;
        }

        if (y < 0) {
            y = 0;
        }
        Log.e("mmmm", "" + mMaxScrollY);

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
            // 滑动的地方
            int currY = mScroller.getCurrY();
            Log.e("fasd", "currY = " + currY + ", " + mScroller.getCurrVelocity());

            scrollTo(0, currY);

            invalidate();
        } else { // 所有滑动一定都会走到这里， 快滑动在最后停止时调用一次， 慢滑动一直调用的这里
            if (mIsUp) { // 手指抬起后去走结束的流程
                mIsUp = false;
                stopScroll();
            }
        }
    }

    private void startScroll() {
        int rootScrollY = getScrollY();
        if (rootScrollY > mTopLoadingViewHeight * 1 / 10
                && rootScrollY < mTopLoadingViewHeight) {
            mTopLoadingView.onPositionChange(1 - rootScrollY * 1.0f / mTopLoadingViewHeight);
        } else if (rootScrollY <= mTopLoadingViewHeight * 1 / 10) {
            mTopLoadingView.onReleaseRefresh();
        }
    }

    private void stopScroll() {
        Log.e("zhuxiaoan", "stop");
        if (mIsRefresh) {
            return;
        }

        int rootScrollY = getScrollY();

        if (rootScrollY > mTopLoadingViewHeight) {
            return;
        }

        if (rootScrollY > mTopLoadingViewHeight * 1 / 10
                && rootScrollY <= mTopLoadingViewHeight) {// 下啦刷新, 但未达到开始刷新的程度
            mScroller.startScroll(0, rootScrollY, 0, mTopLoadingViewHeight - rootScrollY, 0);
            Log.e("zhuxiaoan", "stop 但未达到开始刷新的程度");
            invalidate();

        } else if (rootScrollY < 0 || (rootScrollY >= 0 && rootScrollY <= mTopLoadingViewHeight * 1 / 10)) {// 到达下啦刷新的程度
            mScroller.startScroll(0, rootScrollY, 0, mTopLoadingViewHeight - rootScrollY, 0);
            invalidate();

            Log.e("zhuxiaoan", "stop 刷新");
            mIsRefresh = true;
            mTopLoadingView.onRefresh();
            if (mListener != null) {
                mListener.onRefresh();
            }
        }
    }

    /**
     * 刷新完成
     */
    public void setRefreshComplete() {
        if (mIsRefresh) {
            mIsRefresh = false;
            mScroller.startScroll(0, 0, 0, mTopLoadingViewHeight, 500);
            invalidate();
        }
    }

    /**
     * 设置自动刷新
     */
    public void setAutoRefresh() {
        scrollTo(0, 0);
        mIsRefresh = true;
        if (mListener != null) {
            mListener.onRefresh();
        }
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

}
