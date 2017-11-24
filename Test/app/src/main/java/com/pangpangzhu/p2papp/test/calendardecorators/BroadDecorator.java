package com.pangpangzhu.p2papp.test.calendardecorators;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.pangpangzhu.p2papp.test.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;


/**
 * 日历边框装饰
 * Created by 小稀革 on 2017/3/14.
 */

public class BroadDecorator implements DayViewDecorator {

    private Context mContext;

    public BroadDecorator(Context context) {
        mContext = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        CalendarDay calendarDay = CalendarDay.from(Calendar.getInstance());
        return day.equals(calendarDay);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setmIsBoradable(true);
        view.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }


}
