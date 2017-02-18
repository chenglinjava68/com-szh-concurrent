package com.szh.demo;


import com.szh.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhihaosong on 16-11-28.
 */
public class TestStringValue {
    public static void main(String[] args) {
        String xx=null;
        String redisUid = String.valueOf(xx);
        System.out.println(redisUid);
        if (!StringUtil.isEmpty(redisUid)) {
            System.out.println(redisUid);
            if (redisUid == "null")
                System.out.println(redisUid);

        }


        List<String> list = new ArrayList<String>();
        List<String> failList = new ArrayList<String>();
        for (int i = 0; i <20 ; i++) {
            if(i%2==0)
                failList.add(new Integer(i).toString()+"a");
            list.add(new Integer(i).toString()+"a");
        }
        System.out.println(list.toString()+"\n"+failList.toString());
        if (!failList.isEmpty()) {
            for (String failMs : failList) {
                list.remove(failMs);
            }
        }
        failList.clear();
        System.out.println(list.toString()+"\n"+failList.toString());

        try {
            System.out.println(URLDecoder.decode("%E4%B8%AD%E6%96%87","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
