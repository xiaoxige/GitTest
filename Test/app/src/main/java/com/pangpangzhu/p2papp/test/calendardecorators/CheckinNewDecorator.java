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
 * Created by xiaoxige on 2017/9/19 15:16.
 */

public class CheckinNewDecorator implements DayViewDecorator {

    private Context mContext;

    private List<CalendarDay> mDays;

    public CheckinNewDecorator(Context context) {
        this.mContext = context;
        mDays = new ArrayList<>();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {


        for (CalendarDay calendarDay : mDays) {
            if (calendarDay.equals(day)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        view.setSelected(true);
    }

    public void setDate(List<CalendarDay> mDays) {
        this.mDays = mDays;
    }
}
