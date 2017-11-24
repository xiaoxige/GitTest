package com.pangpangzhu.p2papp.test.calendardecorators;

import android.content.Context;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 日历小点装饰
 */
public class PointDecorator implements DayViewDecorator {

    private int color;
    private List<CalendarDay> dates;
    private Context mContext;

    public PointDecorator(Context context, int color) {
        this.mContext = context;
        this.color = color;
        this.dates = new ArrayList<>();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(8, color));
        view.setSelected(false);
    }

    public void setDates(Collection<CalendarDay> dates) {
        this.dates.addAll(dates);
    }

    public void setData(CalendarDay data) {
        this.dates.add(data);
    }

    public List<CalendarDay> getDatas() {
        return this.dates;
    }

    public void removeCalendarDay(CalendarDay calendar) {
        for (CalendarDay day : dates) {
            if (day.equals(calendar)) {
                dates.remove(day);
            }
        }
    }
}
