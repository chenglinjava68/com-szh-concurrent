package com.szh.util.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
    private static Logger log = LoggerFactory.getLogger(DateUtil.class);

    private DateUtil() {
    }

    public static long getMinutesRelateToCurrentTime(Date relativeDate) {
        return relativeDate != null?(System.currentTimeMillis() - relativeDate.getTime()) / 60000L:0L;
    }

    public static long getDaySpan(Date startDate, Date endDate) {
        return startDate != null && endDate != null?(endDate.getTime() - startDate.getTime()) / 86400000L:0L;
    }

    public static long getSecondsRelateToCurrentTime(Date relativeDate) {
        return relativeDate != null?(System.currentTimeMillis() - relativeDate.getTime()) / 1000L:0L;
    }

    public static String getDateStrRelateToCurrentDate(Date relativeDate) {
        Calendar current = Calendar.getInstance();
        Calendar create = Calendar.getInstance();
        create.setTime(relativeDate);
        long minutesRelateToCurrentTime = getMinutesRelateToCurrentTime(relativeDate);
        if(minutesRelateToCurrentTime <= 0L) {
            minutesRelateToCurrentTime = 1L;
        }

        if(minutesRelateToCurrentTime < 2L) {
            return minutesRelateToCurrentTime + "分钟内";
        } else if(minutesRelateToCurrentTime < 60L) {
            return minutesRelateToCurrentTime + "分钟前";
        } else {
            SimpleDateFormat formatter;
            if(formatDate(create.getTime(), "yyyy-MM-dd").equalsIgnoreCase(formatDate(current.getTime(), "yyyy-MM-dd"))) {
                formatter = new SimpleDateFormat("HH:mm");
                return "今天 " + formatter.format(relativeDate);
            } else {
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                return formatter.format(relativeDate);
            }
        }
    }

    public static String getTimeStrRelateToCurrentTime(Date relativeDate) {
        long minutesRelateToCurrentTime = getMinutesRelateToCurrentTime(relativeDate);
        if(minutesRelateToCurrentTime <= 0L) {
            minutesRelateToCurrentTime = 1L;
        }

        if(minutesRelateToCurrentTime < 60L) {
            return minutesRelateToCurrentTime + "分钟内";
        } else if(minutesRelateToCurrentTime / 60L < 24L) {
            return minutesRelateToCurrentTime / 60L + "小时内";
        } else if(minutesRelateToCurrentTime / 1440L < 30L) {
            return minutesRelateToCurrentTime / 1440L + "天内";
        } else if(minutesRelateToCurrentTime / 43200L < 3L) {
            return minutesRelateToCurrentTime / 43200L + "个月内";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(relativeDate);
        }
    }

    public static String getDateSpanStr(Date startDate, Date endDate) {
        long monthSpan = getDaySpan(startDate, endDate) / 30L;
        return monthSpan > 24L?monthSpan / 12L + "年":monthSpan + "个月";
    }

    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat();
        return format.format(date);
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static Date parseDate(String dateValue, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        try {
            return formatter.parse(dateValue);
        } catch (Exception var4) {
            log.error("Warning in DateUtil#parseDate.", var4);
            return null;
        }
    }

    public static int getDays(Date date) {
        if(date == null) {
            return 0;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(6);
        }
    }
}
