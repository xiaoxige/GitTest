package com.pangpangzhu.p2papp.test.calendardecorators;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.pangpangzhu.p2papp.test.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by guowenhui
 * 2017/3/31
 */

public class MultiDisableDecorator implements DayViewDecorator {
    private final Calendar calendar = Calendar.getInstance();
    private final Context mContext;

    private Collection<CalendarDay> mDates;

    public MultiDisableDecorator(Context context) {
        this.mContext = context;
        mDates = new ArrayList<>();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (mDates.contains(day)) {
            return true;
        }
        if (day.isBefore(CalendarDay.today())) {
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
