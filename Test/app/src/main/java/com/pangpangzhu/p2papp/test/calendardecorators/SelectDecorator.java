package com.pangpangzhu.p2papp.test.calendardecorators;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.pangpangzhu.p2papp.test.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;
import java.util.List;


/**
 * 日历选中装饰
 */
public class SelectDecorator implements DayViewDecorator {

    private Context mContext;

    private List<CalendarDay> mCalendarDays = new ArrayList<>();
    private List<CalendarDay> mNotSelectedDays = new ArrayList<>();

    public SelectDecorator(Context context) {
        this.mContext = context;
        mCalendarDays = new ArrayList<>();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        //今天之前不能装饰
        if (day.isBefore(CalendarDay.today())) {
            return false;
        }
        if (mCalendarDays.contains(day)) {
            return true;
        }
        if (mNotSelectedDays.contains(day)) {
            return false;
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        view.setSelected(true);
        view.setDayDescNum(10);
    }


    public void setDate(List<CalendarDay> calendarDays) {
        mCalendarDays = calendarDays;
    }

    public List<CalendarDay> getCalendarDays() {
        return mCalendarDays;
    }

//    public void setNotSelectedDays(List<CalendarDay> notSelectedDays) {
//        mNotSelectedDays = notSelectedDays;
//    }
}
