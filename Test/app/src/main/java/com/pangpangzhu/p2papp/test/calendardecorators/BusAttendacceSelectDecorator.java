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
 * Created by xiaoxige on 2017/9/21 15:01.
 * 用于安全校车选中的状态的选择器
 */

public class BusAttendacceSelectDecorator implements DayViewDecorator {

    private Context mContext;
    private List<CalendarDay> mDays;

    public BusAttendacceSelectDecorator(Context context) {
        mContext = context;
        mDays = new ArrayList<>();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (mDays.contains(day)) {
            return true;
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        view.setSelected(true);
    }

    public void setDays(List<CalendarDay> days) {
        this.mDays = days;
    }

    public List<CalendarDay> getDays() {
        return mDays;
    }
}
