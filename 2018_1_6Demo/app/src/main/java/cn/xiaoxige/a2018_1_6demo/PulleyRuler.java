package cn.xiaoxige.a2018_1_6demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhuxiaoan on 2018/1/7.
 * 滑轮尺子
 */

public class PulleyRuler extends View {

    private Context mContext;

    private int mViewWidth;
    private int mViewHeight;
    private int mViewCore;
    private int mRuleWidth;

    private Paint mCorePointPaint;
    private Paint mSelectedPaint;
    private Paint mNonSelectedPaint;

    /**
     * 两个刻度之间的间距
     */
    private int mScaleSpacing;
    /**
     * 单位的刻度
     */
    private int mCompanyScale;
    /**
     * 单位峰值
     */
    private int mCompanyPeak;
    /**
     * 开始的刻度
     */
    private int mBgnScale;
    /**
     * 结束的刻度
     */
    private int mEndScale;
    /**
     * 当前进度
     */
    private int mScale;

    // TODO: 2018/1/7 暂不考虑
    private int mTextSize;

    /**
     * 开始画的起点
     */
    private int mStartingPoint;
    /**
     * 结束画的起点
     */
    private int mEndPoint;

    private int mBottomLine;

    private float mDixX;

    private int mPeakLineHeight;
    private int mCompanyHeight;
    private float mPeakProportion;
    private float mCompanyProportion;

    public PulleyRuler(Context context) {
        this(context, null);
    }

    public PulleyRuler(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PulleyRuler(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initData(attrs);
    }

    private void initView() {

        // 中心指示器的画笔
        mCorePointPaint = new Paint();
        mCorePointPaint.setColor(Color.GREEN);
        mCorePointPaint.setAntiAlias(true);
        mCorePointPaint.setStrokeWidth(4);

        // 选择过的画笔
        mSelectedPaint = new Paint();
        mSelectedPaint.setColor(Color.RED);
        mSelectedPaint.setAntiAlias(true);
        mSelectedPaint.setStrokeWidth(6);

        // 选择后面的画笔
        mNonSelectedPaint = new Paint();
        mNonSelectedPaint.setColor(Color.GRAY);
        mNonSelectedPaint.setAntiAlias(true);
        mNonSelectedPaint.setStrokeWidth(2);

    }

    private void initData(AttributeSet attrs) {
        mViewHeight = dip2px(mContext, 30);
        mScaleSpacing = dip2px(mContext, 4);
        mTextSize = dip2px(mContext, 10);
        mPeakProportion = 0.7f;
        mCompanyProportion = 0.3f;
        mCompanyPeak = 10;
        mCompanyScale = 1;
        mBgnScale = 0;
        mEndScale = 100;
        mScale = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        /**
         * 为了确定中心点， 所以view的宽地不能空
         */
        if (widthMode != MeasureSpec.EXACTLY) {
            throw new RuntimeException("pulleyRuler'width must be sure...");
        }
        /**
         * 如果高度确定了就是用确定的高度
         */
        if (heightMode == MeasureSpec.EXACTLY) {
            mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            mViewHeight = mViewHeight + getPaddingTop() + getPaddingBottom();
        }
        mViewCore = mViewWidth / 2;
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        /**
         * 峰值线的高度
         */
        mPeakLineHeight = (int) ((mViewHeight - getPaddingBottom() - getPaddingTop())
                * mPeakProportion);
        /**
         * 单位线高度
         */
        mCompanyHeight = (int) ((mViewHeight - getPaddingBottom() - getPaddingTop())
                * mCompanyProportion);
        /**
         * 底部基线
         */
        mBottomLine = mViewHeight - getPaddingBottom();
        /**
         * 开始话的起点
         */
        mStartingPoint = mViewCore;
        mRuleWidth = (mEndScale - mBgnScale) * mScaleSpacing / mCompanyScale;
        mEndPoint = mStartingPoint + mRuleWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("TAG", "峰值线的高度 = " + mPeakLineHeight +
                ", " + "单位线高度 = " + mCompanyHeight +
                ", " + "开始话的起点 = " + mStartingPoint);

        // 画中心指针
        drawCorePoint(canvas);
        drawRule(canvas);

    }

    private float mStartX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                changeRulePositionByDis((int) (endX - mStartX));
                if (mStartingPoint >= mViewCore) {
                    changeRulePosition(mViewCore);
                }
                if (mStartingPoint <= mViewCore - mRuleWidth) {
                    changeRulePosition(mViewCore - mRuleWidth);
                }
                mStartX = endX;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private void drawCorePoint(Canvas canvas) {
        canvas.drawLine(mViewCore, 0
                , mViewCore, mBottomLine, mCorePointPaint);
    }

    private void drawRule(Canvas canvas) {

        for (int scale = mBgnScale, point = mStartingPoint;
             scale <= mEndScale && point <= mEndPoint;
             scale += mCompanyScale, point += mScaleSpacing) {
            // 开始、结束、一次峰值
            if (scale == mBgnScale || scale == mEndScale || (mBgnScale - scale) % mCompanyPeak == 0) {
                canvas.drawLine(point, mBottomLine - mPeakLineHeight,
                        point, mBottomLine,
                        mNonSelectedPaint);
            } else {
                canvas.drawLine(point, mBottomLine - mCompanyHeight,
                        point, mBottomLine,
                        mNonSelectedPaint);
            }
            if (scale != mEndScale) {
                canvas.drawLine(point, mBottomLine, point + mScaleSpacing, mBottomLine, mNonSelectedPaint);
            }
        }

    }

    private void changeRulePosition(int ruleBgn) {
        mStartingPoint = ruleBgn;
        changeRulePositionByDis(0);
    }

    private void changeRulePositionByDis(int disX) {
        mStartingPoint += disX;
        mEndPoint = mStartingPoint + (mEndScale - mBgnScale) * mScaleSpacing / mCompanyScale;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
