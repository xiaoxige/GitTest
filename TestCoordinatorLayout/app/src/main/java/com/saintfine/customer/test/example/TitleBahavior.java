package com.saintfine.customer.test.example;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.saintfine.customer.test.R;

/**
 * @author by zhuxiaoan on 2018/7/16 0016.
 */

public class TitleBahavior extends CoordinatorLayout.Behavior<FrameLayout> {

    private FrameLayout flTitleAlphaContailer;

    public TitleBahavior() {
    }

    public TitleBahavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FrameLayout child, View dependency) {
        if (dependency.getId() == R.id.llBannerContailer) {
            flTitleAlphaContailer = (FrameLayout) child.findViewById(R.id.flTitleAlphaContailer);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FrameLayout child, View dependency) {

        // 滚动的距离
        float translationY = Math.abs(dependency.getTranslationY());

        // 滚动的最大距离
        int maxTranslationY = Math.abs(dependency.getMeasuredHeight() - child.getMeasuredHeight());

        float v = translationY / maxTranslationY;

        flTitleAlphaContailer.setAlpha(v);

        return true;
    }
}
