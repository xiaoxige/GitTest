package com.saintfine.customer.test.example;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.saintfine.customer.test.R;

/**
 * @author by zhuxiaoan on 2018/7/16 0016.
 */

public class ExampleViewPagerBehavior extends CoordinatorLayout.Behavior<ViewPager> {

    private Context mContext;
    private TabLayout tabLayout;
    private FrameLayout llTitleContailer;

    public ExampleViewPagerBehavior() {
    }

    public ExampleViewPagerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ViewPager child, View dependency) {
        if (dependency.getId() == R.id.tabLayout) {
            tabLayout = (TabLayout) parent.findViewById(R.id.tabLayout);
            llTitleContailer = (FrameLayout) parent.findViewById(R.id.llTitleContailer);
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ViewPager child, View dependency) {

        int delta = (int) (dependency.getTranslationY() + dependency.getBottom());
        delta = delta - child.getTop();
        child.offsetTopAndBottom(delta);
        return true;
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, ViewPager child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        int top = tabLayout.getMeasuredHeight() + llTitleContailer.getMeasuredHeight();
        child.measure(parentWidthMeasureSpec, parentHeightMeasureSpec - top);
        return true;
    }
}
