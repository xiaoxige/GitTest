package com.saintfine.customer.test.example;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;

import com.saintfine.customer.test.R;

/**
 * @author by zhuxiaoan on 2018/7/16 0016.
 */

public class ExampleTabLayoutBehavior extends CoordinatorLayout.Behavior<TabLayout> {

    public ExampleTabLayoutBehavior() {
    }

    public ExampleTabLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TabLayout child, View dependency) {
        return dependency.getId() == R.id.llBannerContailer;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TabLayout child, View dependency) {
        int delta = (int) (dependency.getTranslationY() + dependency.getBottom());
        delta = delta - child.getTop();
        child.offsetTopAndBottom(delta);
        return true;
    }
}
