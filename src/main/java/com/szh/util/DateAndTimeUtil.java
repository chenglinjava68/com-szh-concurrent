package com.szh.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateAndTimeUtil {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 日期月份减一个月
     */
    public static String dateFormat(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = sdf.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.MONTH, -1);
        date = cl.getTime();
        return sdf.format(date);
    }

    public static String dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }

    /**
     * 日期加3天
     */
    public static String DateAdd3Days(String _date) {
        Calendar cl = Calendar.getInstance();
        Date date = null;
        try {
            date = (Date) simpleDateFormat.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cl.setTime(date);
        cl.add(Calendar.DAY_OF_YEAR, 3);
        date = cl.getTime();
        return simpleDateFormat.format(date);
    }

    /**
     * 日期加3天
     */
    public static String DateAdd3Days(Date _date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(_date);
        cl.add(Calendar.DAY_OF_YEAR, 3);
        _date = cl.getTime();
        return simpleDateFormat.format(_date);
    }

    /**
     * 日期加n天
     */
    public static String DateAddDays(Date _date, int days) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(_date);
        cl.add(Calendar.DAY_OF_YEAR, days);
        _date = cl.getTime();
        return simpleDateFormat.format(_date);
    }

    /**
     * 日期加减n天
     */
    public static String DateAddSubDays(Date _date, int sums) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(_date);
        cl.add(Calendar.DAY_OF_YEAR, 0 - sums);
        _date = cl.getTime();
        return simpleDateFormat.format(_date);
    }

    public static void main(String[] args) throws ParseException {
        String str = null;
        System.out.println(new Date());
        System.out.println(DateAdd3Days(new Date()));

    }
}