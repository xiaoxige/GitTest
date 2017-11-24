package com.pangpangzhu.p2papp.test.calendardecorators;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.pangpangzhu.p2papp.test.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;



/**
 * 日历默认装饰
 */
public class DefaultDecorator implements DayViewDecorator {

    private final Calendar calendar = Calendar.getInstance();
    private final Drawable highlightDrawable;
    private static final int color = Color.parseColor("#006BBA");
    private final Context mContext;

    public DefaultDecorator(Context context) {
        this.mContext = context;
        highlightDrawable = new ColorDrawable(color);
        //        highlightDrawable = context.getResources().getDrawable(R.drawable.materialcalendarview_green_selector);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
//        if (!day.isAfter(CalendarDay.today())) {
//            return true;
//        }
//
//        return false;
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setTextColor(ContextCompat.getColor(mContext, R.color.materialcalendarview_color_default));
    }
}
