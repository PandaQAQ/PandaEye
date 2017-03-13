package com.pandaq.pandaeye.utils;

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
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.MONDAY:
                return Constants.MONDAY;
            case Calendar.TUESDAY:
                return Constants.TUESDAY;
            case Calendar.WEDNESDAY:
                return Constants.WEDNESDAY;
            case Calendar.THURSDAY:
                return Constants.THURSDAY;
            case Calendar.FRIDAY:
                return Constants.FRIDAY;
            case Calendar.SATURDAY:
                return Constants.SATURDAY;
            case Calendar.SUNDAY:
                return Constants.SUNDAY;
            default:
                return null;
        }
    }

    public static String formatDate(Calendar calendar) {
        DateFormat df = SimpleDateFormat.getDateInstance();
        return df.format(calendar.getTime());
    }

}
