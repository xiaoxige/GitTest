package com.saintfine.customer.test.xiaoxigetest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
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
        Log.e("TAG", " ~==~ target = " + target + "dy = " + dy + ", consumed[1] = " + consumed[1]);
        // dy 上 +， 下 -
        int childCount = getChildCount();
        if (dy > 0) {
//        if (false) {
            // 上（从后向前通知）+
            for (int index = childCount - 1; index >= 0; index--) {
                int[] food = infiltration(getChildAt(index), target, dx, dy);
                int consumptionX = food[0];
                int consumptionY = food[1];
                dx -= consumptionX;
                dy -= consumptionY;
                consumed[0] += consumptionX;
                consumed[1] += consumptionY;
                if (dy <= 0) {
                    break;
                }
            }
        } else {
            // 下（从前向后通知）-
            for (int index = 0; index < childCount; index++) {
                int[] food = infiltration(getChildAt(index), target, dx, dy);
                int consumptionX = food[0];
                int consumptionY = food[1];
                dx -= consumptionX;
                dy -= consumptionY;
                consumed[0] += consumptionX;
                consumed[1] += consumptionY;
                if (dy >= 0) {
                    break;
                }
            }
        }
        Log.e("TAG", "target = " + target + "dy = " + dy + ", consumed[1] = " + consumed[1]);
//        Log.e("TAG", "target = " + target + "consumed[1] = " + consumed[1]);
//        for (int index = 0; index < childCount; index++) {
//            childView = getChildAt(index);
//            if (childView.equals(target)) {
//                continue;
//            }
//            if (childView instanceof NestedScrollingChild) {
//                int[] food = new int[2];
//                ((NestedScrollingChild) childView).dispatchNestedPreScroll(dx, dy, food, null);
//                int consumptionX = food[0];
//                int consumptionY = food[1];
//                consumed[0] += consumptionX;
//                consumed[1] += consumptionY;
//                dx -= consumptionX;
//                dy -= consumptionY;
//            }
//        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return true;
    }

    /**
     * 渗透给View，让其自己确定是否消费以及消费多少
     *
     * @param childView
     * @param dx
     * @param dy
     * @return
     */
    private int[] infiltration(View childView, View targetView, int dx, int dy) {
        int[] food = {
                0, 0
        };
        infiltration(childView, targetView, dx, dy, food);
        return food;
    }

    private void infiltration(View childView, View targetView, int dx, int dy, int[] consumed) {
        if (!targetView.equals(childView) && childView instanceof NestedScrollingChild) {
            int[] food = new int[2];
            ((NestedScrollingChild) childView).dispatchNestedPreScroll(dx, dy, food, null);
            int consumptionX = food[0];
            int consumptionY = food[1];
            dx -= consumptionX;
            dy -= consumptionY;
            consumed[0] += consumptionX;
            consumed[1] += consumptionY;
        }

        if (childView instanceof ViewGroup) {
            int childCount = ((ViewGroup) childView).getChildCount();
            if (dy > 0) {
                for (int index = childCount - 1; index >= 0; index--) {
                    infiltration(((ViewGroup) childView).getChildAt(index), targetView, dx, dy, consumed);
                }
            } else {
                for (int index = 0; index < childCount; index++) {
                    infiltration(((ViewGroup) childView).getChildAt(index), targetView, dx, dy, consumed);
                }
            }
        }

    }
}
