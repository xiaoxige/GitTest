package com.saintfine.customer.test.example;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.saintfine.customer.test.R;

/**
 * @author by zhuxiaoan on 2018/7/16 0016.
 */

public class BannerBehavior extends CoordinatorLayout.Behavior<LinearLayout> {

    private TabLayout tabLayout;
    private FrameLayout llTitleContailer;

    public BannerBehavior() {
    }

    public BannerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, LinearLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        tabLayout = (TabLayout) coordinatorLayout.findViewById(R.id.tabLayout);
        llTitleContailer = (FrameLayout) coordinatorLayout.findViewById(R.id.llTitleContailer);
        return target.getId() == R.id.recyclerView;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, LinearLayout child, View target, int dx, int dy, int[] consumed) {
        int childHeight = child.getMeasuredHeight() - llTitleContailer.getMeasuredHeight();
        // 在原来位置上是负值， 在原来位置下面是正值
        float childTranslationY = child.getTranslationY();

        float childTranslationDis = Math.abs(childTranslationY);

        if (dy > 0) {
            // 向上滑

            if (childTranslationDis >= childHeight) {
                super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
            } else if (childTranslationDis < childHeight) {

                if (dy <= childHeight - childTranslationDis) {
                    consumed[1] = dy;
                } else {
                    consumed[1] = (int) (childHeight - childTranslationDis);
                }
                child.setTranslationY(-(childTranslationDis + consumed[1]));

            } else {
                super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
            }

        } else {
            // 向下滑

            int dyDis = Math.abs(dy);

            if ((target).canScrollVertically(-1)) {
                // 还未滑动到顶部
                super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
                return;
            }

            if (childTranslationDis <= 0) {
                super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
            } else {
                if (childTranslationDis >= dyDis) {
                    consumed[1] = dy;
                } else {
                    consumed[1] = (int) -childTranslationDis;
                }

                child.setTranslationY(-(childTranslationDis - Math.abs(consumed[1])));
            }

        }
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, LinearLayout child, View target, float velocityX, float velocityY) {
        int childHeight = child.getMeasuredHeight() - llTitleContailer.getMeasuredHeight();
        // 在原来位置上是负值， 在原来位置下面是正值
        float childTranslationY = child.getTranslationY();

        float childTranslationDis = Math.abs(childTranslationY);

        if (childTranslationDis >= childHeight) {

            return false;
        }

        return true;
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, LinearLayout child, View target, float velocityX, float velocityY, boolean consumed) {

        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

}
