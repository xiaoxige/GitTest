package com.pangpangzhu.p2papp.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.pangpangzhu.p2papp.test.calendardecorators.BroadDecorator;
import com.pangpangzhu.p2papp.test.calendardecorators.BusAttendacceSelectDecorator;
import com.pangpangzhu.p2papp.test.calendardecorators.DefaultDecorator;
import com.pangpangzhu.p2papp.test.calendardecorators.DescDecorator;
import com.pangpangzhu.p2papp.test.calendardecorators.DisableDecorator;
import com.pangpangzhu.p2papp.testlibrary.ZyaoAnnotation;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ZyaoAnnotation(name = "xiaoxige", text = "hello, I'm Zhuxiaoan")
public class MainActivity extends AppCompatActivity {

    private MaterialCalendarView materialCalendarView;

    DisableDecorator disableDecorator;

    BroadDecorator broadDecorator;
    private BusAttendacceSelectDecorator mSelectDecorator = new BusAttendacceSelectDecorator(MainActivity.this);
    private DescDecorator descDecorator = new DescDecorator(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        materialCalendarView = (MaterialCalendarView) findViewById(R.id.materialCalendarView);

        disableDecorator = new DisableDecorator(this);
        broadDecorator = new BroadDecorator(this);

        // 三年前
        Calendar mixTime = Calendar.getInstance();
        mixTime.add(Calendar.MONTH, -3 * 12);

        // 二十年后
        Calendar maxTime = Calendar.getInstance();
        maxTime.add(Calendar.YEAR, 5);
        maxTime.set(Calendar.DAY_OF_MONTH, maxTime.getActualMaximum(Calendar.DAY_OF_MONTH));

        materialCalendarView.addDecorators(
                new DefaultDecorator(this),
                disableDecorator,
                broadDecorator,
                descDecorator,
                mSelectDecorator
        );

        materialCalendarView.setTopbarVisible(true);
        materialCalendarView.setDynamicHeightEnabled(true);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        materialCalendarView.state().edit()
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .setMinimumDate(mixTime.getTime())
                .setMaximumDate(maxTime.getTime())
                .commit();

        materialCalendarView.setSelectedDate(CalendarDay.today());
        materialCalendarView.invalidateDecorators();

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                List<CalendarDay> dates = new ArrayList<>();
                dates.add(date);
                mSelectDecorator.setDays(dates);
                descDecorator.setDays(dates);
                materialCalendarView.invalidateDecorators();
            }

            @Override
            public void onDisableDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

            }
        });
    }
}
