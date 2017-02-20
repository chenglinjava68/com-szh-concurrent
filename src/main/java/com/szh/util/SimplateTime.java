package com.szh.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhihaosong on 16-8-9.
 */
public class SimplateTime {
    public static void main(String[] args) {
        System.out.print((new SimpleDateFormat("yyyy-MM-dd")).format(new Date().getTime()-24*60*60*1000));

    }
}
