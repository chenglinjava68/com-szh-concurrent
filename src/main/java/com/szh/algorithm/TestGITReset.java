package com.szh.algorithm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhihaosong on 16-10-31.
 */
public class TestGITReset {
    static DateFormat df8 = new SimpleDateFormat("yyyy-MM-dd 08:00:00");
    static DateFormat df18 = new SimpleDateFormat("yyyy-MM-dd 15:56:00");
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        try {
            Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        Date date8 = df.parse(df8.format(new Date()));
                        Date date18 = df.parse(df18.format(new Date()));
                        System.out.println(date8);
                        System.out.println(date18);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 5, 5, TimeUnit.SECONDS);


            Date date8 = df.parse(df8.format(new Date()));
            Date date18 = df.parse(df18.format(new Date()));
            System.out.println(date8);
            System.out.println(date18);
            Date date = new Date();
            if (date.compareTo(date8) > 0 && date.compareTo(date18) < 0) {
                System.out.println(1 + "\t" + date.compareTo(date8));
                System.out.println(2 + "\t" + date.compareTo(date18));
            } else System.out.println(3);

/*
            if (dddf.compareTo(new Date()) < 0) {
                System.out.println(dddf.compareTo(new Date()));

            } else
                System.out.println("sss:" + dddf.compareTo(new Date()));


            String DateStr1 = "2011-10-1 10:20:16";
            String DateStr2 = "2011-10-07 15:50:35";
            String ssssss = "2016-11-14 18:00:00";
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateTime1 = null;
            try {
                dateTime1 = dateFormat.parse(ssssss);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date dateTime2 = null;
            try {
                dateTime2 = dateFormat.parse(DateStr2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(dateTime1);
            int i = dateTime1.compareTo(dateTime2);
            System.out.println(i < 0);
*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
