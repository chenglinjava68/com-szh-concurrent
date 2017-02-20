package com.szh.util;


import java.math.BigInteger;

/**
 * Created by zhihaosong on 16-12-7.
 */
public class shortURL {
    private static String[] chars = new String[]{"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "main.java.com.szh.reflect.A", "main.java.com.szh.reflect.B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    private static final int CODE_SIZE = 7;
    private static final int CODE_SIZE_4BIZ = 10;

    public static void main(String[] args) {
        System.out.println(chars.length+"\t");
        String url = "https://www.baidu.com/s?tn=ubuntrcid=20986&rt=%E7%9B%B8%E5%85%B3%E4%B9%A6%E7%B1%8D&euri=5e8d496f42db4eec935fcab0906fefd4";
//
        String resCode = "";
        long start = System.currentTimeMillis();
        int retryCount = 0;
        for (; retryCount < 3; ++retryCount) {
            String key = "";
            if (retryCount == 0) {//update  business_type set name = '人才库群发消息' where id= 4034; update  business_type set name = '悬赏邀约' where id= 4033;
                key = "dajie";
            } else {
                key = "dajie" + String.valueOf(retryCount);
            }
            String sMD5EncryptResult = (new MD5()).toHexString(key + url);
            System.out.println(sMD5EncryptResult+"\t"+sMD5EncryptResult.length());

            BigInteger md5num = new BigInteger(sMD5EncryptResult, 16);
            System.out.println(md5num+"\t"+md5num.bitLength());

            md5num = new BigInteger(md5num.toString());
            System.out.println(md5num);

            BigInteger scale = new BigInteger("62");
            System.out.println(scale + "\n" + "========");

            for (int i = 0; i < CODE_SIZE_4BIZ; ++i) {
                resCode = chars[md5num.mod(scale).intValue()] + resCode;
                System.out.println(resCode);

                md5num = md5num.divide(scale);
                System.out.println(md5num);

            }

            final String myShortURL = resCode;
            final String myOriginURL = url;
            System.out.println("========\n" + resCode + "\t" + url);
            break;
        }
    }
}
