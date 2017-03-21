package com.pandaq.pandaeye.utils;

import com.pandaq.pandaeye.CustomApplication;
import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.config.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by PandaQ on 2017/3/13.
 * 日期处理工具类
 */

public class DateUtils {
    public static String getWeek(Calendar calendar) {
        String[] week = CustomApplication.getContext().getResources().getStringArray(R.array.week_array);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.MONDAY:
                return week[0];
            case Calendar.TUESDAY:
                return week[1];
            case Calendar.WEDNESDAY:
                return week[2];
            case Calendar.THURSDAY:
                return week[3];
            case Calendar.FRIDAY:
                return week[4];
            case Calendar.SATURDAY:
                return week[5];
            case Calendar.SUNDAY:
                return week[6];
            default:
                return null;
        }
    }

    public static String formatDate(Calendar calendar) {
        DateFormat df = SimpleDateFormat.getDateInstance();
        return df.format(calendar.getTime());
    }

}
