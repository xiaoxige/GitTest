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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;



/**
 * 日历禁用装饰
 */
public class DisableDecorator implements DayViewDecorator {

    private final Calendar calendar = Calendar.getInstance();
    private final Drawable highlightDrawable;
    private static final int color = Color.parseColor("#006BBA");
    private final Context mContext;

    private Collection<CalendarDay> mDates;

    public DisableDecorator(Context context) {
        this.mContext = context;
        highlightDrawable = new ColorDrawable(color);
        mDates = new ArrayList<>();
        //        highlightDrawable = context.getResources().getDrawable(R.drawable.materialcalendarview_green_selector);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
//        if (day.isAfter(CalendarDay.today())) {
//            return true;
//        }

        //6的倍数日也禁用
//        if (day.getDay() % 6 == 0) {
//            return true;
//        }

//        day.copyTo(calendar);
//        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
//        if (weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY) {
//            return true;
//        }

        if (mDates.contains(day)) {
            return true;
        }

        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDisabled(true);
        view.setTextColor(ContextCompat.getColor(mContext, R.color.materialcalendarview_color_disable));
    }

    public void setDisableData(Collection<CalendarDay> dates) {
        mDates.addAll(dates);
    }

    public Collection<CalendarDay> getDisableData() {
        return mDates;
    }
}
