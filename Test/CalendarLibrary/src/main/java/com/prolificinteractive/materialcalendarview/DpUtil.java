package com.prolificinteractive.materialcalendarview;

import android.content.Context;

/**
 *
 */
public class DpUtil {

    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int dip2px(Context context, float px) {
        final float scale = getScreenDensity(context);
        return (int) (px * scale + 0.5);
    }
}
