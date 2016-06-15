package com.example.app.androidelem;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;

import cn.zhikaizhang.calendarview.CalendarView;
import cn.zhikaizhang.calendarview.ICalendarView;

/**
 * Created by Administrator on 2016/5/27.
 */
public class CalendarViewActivity extends Activity {
    private CalendarView calendarView;
    int year, month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        initView();
    }

    private void initView() {
        calendarView.setMode(0);
        //refresh the CalendarView with new values of year and month
        calendarView.refresh0(2016, 5);
        //simulate to get data
        /*int days = calendarView.daysOfCurrentMonth();
        boolean data[] = new boolean[days+1];
        int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        for(int i = 1; i <= days; i++) {
            data[i] = i <= today && (Math.random() > 0.5);
        }
        calendarView.refresh1(data);*/

        /**
         * modify the language of head of the calendar
         * legal values of style : 0 - 3
         * 0, 1, 2 : Chinese, 3 : English
         */
        calendarView.setWeekTextStyle(2);

        //set the text color of the head
        calendarView.setWeekTextColor(Color.BLACK);

        /**
         * set the scale of text size of the head
         * legal values : 0.0f - 1.0f
         */
        calendarView.setWeekTextSizeScale(0.5f);

        //set the text color of the calendar cell
        calendarView.setCalendarTextColor(Color.BLACK);

        /**
         * set the scale of text size of the calendar cell
         * legal values : 0.0f - 1.0f
         */
        calendarView.setTextSizeScale(0.5f);

        calendarView.setOnRefreshListener(new ICalendarView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(CalendarViewActivity.this, "refresh", Toast.LENGTH_SHORT).show();
            }
        });

        calendarView.setOnItemClickListener(new ICalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(int day) {
                int year = calendarView.getYear();
                int month = calendarView.getMonth();
                Toast.makeText(CalendarViewActivity.this, year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
