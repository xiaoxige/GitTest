package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 小稀革 on 2017/3/29.
 */

public class WeekTitleLayout extends ViewGroup {

    private Context mContext;

    private static final int NUMDAYOFWEEK = 7;

    private String[] mWeekTitles = {
            "日", "一", "二", "三", "四", "五", "六"
    };
    private int mWeekTextColor;
    private int mWeekTextSize;
    private int mWeekWidth;
    private int mWeekHeight;

    public WeekTitleLayout(Context context) {
        this(context, null);
    }

    public WeekTitleLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekTitleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initDatas();
        initViews();
    }

    private void initViews() {
        for (int i = 0; i < NUMDAYOFWEEK; i++) {
            TextView weekView = createWeekView(mWeekTitles[i]);
            addView(weekView);
        }
        setBackgroundColor(Color.WHITE);
        invalidateWeek();
    }

    private void initDatas() {
        mWeekTextColor = Color.BLACK;
        mWeekTextSize = 14;
    }

    private TextView createWeekView(String title) {

        TextView weekView = new TextView(mContext);
        weekView.setGravity(Gravity.CENTER);
        weekView.setText(title);
        return weekView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        mWeekWidth = (measuredWidth - (NUMDAYOFWEEK - 1) * 10) / NUMDAYOFWEEK;
        mWeekHeight = mWeekWidth;
        View child;
        for (int i = 0; i < NUMDAYOFWEEK; i++) {
            child = getChildAt(i);
            LayoutParams params = new LayoutParams(mWeekWidth, mWeekHeight);
            child.setLayoutParams(params);
            measureChild(child, mWeekWidth, mWeekHeight);
        }
        setMeasuredDimension(measuredWidth, mWeekHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        View child;
        for (int i = 0; i < NUMDAYOFWEEK; i++) {
            child = getChildAt(i);
            child.layout(left, 0, left + mWeekWidth, mWeekHeight);
            left += mWeekWidth + 10;
        }
    }

    public void setWeekTitles(String[] weekTitles) {
        this.mWeekTitles = weekTitles;
        invalidateWeek();
    }

    public void setWeekTextColor(int color) {
        mWeekTextColor = color;
        invalidateWeek();
    }

    public void setWeekTextSize(int size) {
        mWeekTextSize = size;
        invalidateWeek();
    }

    public void setWeekViewColor(int color) {
        setBackgroundColor(color);
    }

    private void invalidateWeek() {
        if (getChildCount() != NUMDAYOFWEEK) {
            return;
        }
        TextView child;
        for (int i = 0; i < NUMDAYOFWEEK; i++) {
            child = (TextView) getChildAt(i);
            child.setTextColor(mWeekTextColor);
            child.setText(mWeekTitles[i]);
            child.setTextSize(mWeekTextSize);
        }
    }

}
