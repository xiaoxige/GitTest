package com.saintfine.customer.test;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * @author by zhuxiaoan on 2018/7/16 0016.
 */

public class RecyclerViewBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    public RecyclerViewBehavior() {
    }

    public RecyclerViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        return dependency instanceof TextView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {

        int delta = (int) (dependency.getTranslationY() + dependency.getBottom());
        delta = delta - child.getTop();
        child.offsetTopAndBottom(delta);
        return true;
    }
}
