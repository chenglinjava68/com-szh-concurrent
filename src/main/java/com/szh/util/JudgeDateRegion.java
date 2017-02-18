package com.szh.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhihaosong on 16-11-14.
 */
@Component
public class JudgeDateRegion {
    private static Logger logger = LoggerFactory.getLogger(JudgeDateRegion.class);
    private static DateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final long oneDay = 24 * 60 * 60 * 1000;

    private static Date date8 = null;
    private static Date date18 = null;

    private void init() {
        date8 = getDateFixed("08:00:00");
        date18 = getDateFixed("18:00:00");
        logger.info("init today:{} date8:{} date18:{}", dateFormat.format(new Date()), date8, date18);
        executeEightAtNightPerDay();
    }

    public JudgeDateRegion() {
        init();
    }

    private void executeEightAtNightPerDay() {
        long initDelay = getDateFixed("00:00:30").getTime() - System.currentTimeMillis();//每天凌晨30s执行
        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    date8 = getDateFixed("08:00:00");
                    date18 = getDateFixed("18:00:00");
                    logger.info("executeEightAtNightPerDay today:{} date8:{} date18:{}", dateFormat.format(new Date()), date8, date18);
                } catch (Exception e) {
                    logger.error("executeEightAtNightPerDay Error:", e);
                }
            }
        }, initDelay, oneDay, TimeUnit.MILLISECONDS);
    }

    private static Date getDateFixed(String time) {
        try {
            return dateFormat.parse(dayFormat.format(new Date()) + " " + time);
        } catch (ParseException e) {
            logger.error("getDateFixed Error:", e);
        }
        return null;
    }

    public static boolean getTimeBetween8And18(Date date) {
        try {
            return (date.compareTo(date8) > 0 && date.compareTo(date18) < 0);  //在8点到18点之间允许发送
        } catch (Exception e) {
            logger.error("getTimeBetween8And18 Error:", e);
            return true;//异常后可以发送
        }

    }
}
