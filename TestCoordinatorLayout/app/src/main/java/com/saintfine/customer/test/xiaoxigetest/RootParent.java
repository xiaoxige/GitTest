package com.saintfine.customer.test.xiaoxigetest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class RootParent extends LinearLayout implements NestedScrollingParent {

    private Context mContext;


    public RootParent(Context context) {
        this(context, null);
    }

    public RootParent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RootParent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setOrientation(LinearLayout.VERTICAL);
    }


    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);

        int childCount = getChildCount();
        View childView;
        for (int index = 0; index < childCount; index++) {
            childView = getChildAt(index);
            if (childView.equals(target)) {
                continue;
            }
            if (childView instanceof NestedScrollingChild) {
                int[] food = new int[2];
                ((NestedScrollingChild) childView).dispatchNestedPreScroll(dx, dy, food, null);
                int consumptionX = food[0];
                int consumptionY = food[1];
                consumed[0] += consumptionX;
                consumed[1] += consumptionY;
                dx -= consumptionX;
                dy -= consumptionY;
            }
        }
    }
}
