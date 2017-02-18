package com.szh.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhihaosong on 16-7-14.
 */
public class TestEmpty {

    public static void main(String[] args) {
        int str = 10;
        List<String> aaa = new ArrayList<String>();
        if(aaa==null){
            System.out.println("sss");

        }
        System.out.println(aaa.size());
        aaa.add("");
        System.out.println(aaa.size());
        aaa.add("sss");
        System.out.println(aaa.size());
    }
}
