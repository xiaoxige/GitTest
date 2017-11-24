package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 小稀革 on 2017/3/29.
 */

public class WeekTitleView extends View {

    private Context mContext;

    private static final int NUMDAYOFWEEK = 7;

    private int mWeekWidth;
    private int mWeekHeight;
    private int mLeftDistance;
    private int mTopDistance;
    private int mTextSize;
    private Paint mPaint;
    private int mPaddingLeft;

    private String[] mWeekTitles = {
            "日", "一", "二", "三", "四", "五", "六"
    };

    private int mTextColor;

    public WeekTitleView(Context context) {
        this(context, null);
    }

    public WeekTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initDatas();
        initViews();
    }

    private void initDatas() {
        mTextSize = 40;
        mPaddingLeft = 10;
        mPaint = new Paint();
        mTextColor = Color.BLACK;
        setBackgroundColor(Color.WHITE);
        invalidateWeekView();
    }

    private void initViews() {
    }

    private void invalidateWeekView() {
        mPaint.setTextSize(mTextSize);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mTextColor);
        invalidate();
    }

    public void setTextSize(int textSize) {
        mTextSize = textSize;
        invalidateWeekView();
    }

    public void setWeekTitles(String[] weekTitles) {
        this.mWeekTitles = weekTitles;
        invalidateWeekView();
    }

    public void setTextColor(int color) {
        this.mTextColor = color;
        invalidateWeekView();
    }

    public void setWeekViewBlackground(int color) {
        setBackgroundColor(color);
    }

    public void setTextTypeFace(Typeface face) {
        mPaint.setTypeface(face);
        invalidateWeekView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        mWeekWidth = (measuredWidth - (NUMDAYOFWEEK - 1) * 10) / NUMDAYOFWEEK;
        mWeekHeight = mWeekWidth;

        mLeftDistance = (mWeekWidth - mTextSize) / 2;
        mTopDistance = (mWeekHeight - mTextSize) / 2;

        setMeasuredDimension(measuredWidth, mWeekHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int left = mLeftDistance;
        for (int i = 0; i < NUMDAYOFWEEK; i++) {
            canvas.drawText(mWeekTitles[i], left, mTextSize + mTopDistance, mPaint);
            left += mWeekWidth + mPaddingLeft;
        }
    }

}
