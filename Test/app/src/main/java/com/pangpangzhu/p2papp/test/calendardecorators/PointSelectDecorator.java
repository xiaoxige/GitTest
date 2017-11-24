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
 * Created by guowenhui
 * 2017/4/1
 */

public class PointSelectDecorator implements DayViewDecorator {
    private Context mContext;
    private List<CalendarDay> mCalendarDays = new ArrayList<>();

    public PointSelectDecorator(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (mCalendarDays.contains(day)) {
            return true;
        }
        if (day.equals(CalendarDay.today())) {
            return false;
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {

        view.setTextColor(ContextCompat.getColor(mContext, R.color.materialcalendarview_color_default));
        view.setSelected(false);
    }

    public void setDate(List<CalendarDay> calendarDays) {
        mCalendarDays = calendarDays;
    }
}
