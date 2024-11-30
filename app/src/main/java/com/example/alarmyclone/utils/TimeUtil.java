package com.example.alarmyclone.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeUtil {

    public enum DateFormatPattern {

        FULL_DATE("yyyy-MM-dd HH:mm:ss"),
        DATE_ONLY("yyyy-MM-dd"),
        TIME_ONLY("HH:mm:ss"),
        HOUR_MINUTE_FORMAT("HH:mm"),
        MONTH_DAY_YEAR("MM/dd/yyyy"),
        YEAR_MONTH_DAY("yyyy/MM/dd"),
        DAY_MONTH_YEAR("dd/MM/yyyy");

        public final String pattern;

        DateFormatPattern(String pattern) {
            this.pattern = pattern;
        }
    }

    public static String formatCalendarTime(Calendar calendar, DateFormatPattern dateFormat, Locale locale){
        if (locale == null) {
            locale = Locale.getDefault(); // Use default locale if none provided
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat.pattern,locale);
        return sdf.format(calendar.getTime());
    }
}
