package com.szh.demo;

import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicLong;


public class ShortUrlGenerator {
    private static final char[] DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                    'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final String MD5_KEY_PRE = "zhihaoSong";
    private static final int NUMBER_61 = 0x0000003D;
    private static final int BIT_LENGTH_30 = 0x3FFFFFFF;

    /**
     * 基于MD5码
     * 这种算法计算的短网址长度一般是5位或者6位，计算过程中可能出现碰撞（概率很小），可表达的url数量为62的5次方或6次方。
     * a.计算长地址的MD5码，将32位的MD码分成4段，每段8个字符
     * b.将a得到的8个字符串看成一个16进制的数，与N * 6个1表示的二进制数进行&操作得到一个N * 6长的二进制数
     * c.将b得到的数分成N段，每段6位，然后将这N个6位数分别与61进行&操作，将得到的数作为INDEX去字母表取相应的字母或数字，
     * 拼接就是一个长度为N的短网址。
     **/
    public static String shortUrlForMD5(String url) {
        // 1. URL MD5加密生产长度为32的串
        String hex = DigestUtils.md5Hex(MD5_KEY_PRE + url);
        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, (i + 1) * 8);
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = BIT_LENGTH_30 & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D(61) 进行位与运算，取得字符数组 chars 索引，相当于取余操作。
                long index = NUMBER_61 & lHexLong;
                // 把取得的字符相加
                outChars += DIGITS[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        for (int i = 0; i < resUrl.length; i++) {
            System.out.println(resUrl[i]);
        }
        return resUrl[new Random().nextInt(resUrl.length)];
    }


    /**
     * 费时操作集中在BigInteger生成上数字上，
     * 可以生成长度较长的串。39
     *
     * @param url
     * @return
     */
    private static final int SHORT_URL_LEN = 15;

    public static String generateShortUrl(String url) {
        String resCode = "";
        // long start = System.nanoTime();
        String sMD5EncryptResult = DigestUtils.md5Hex(MD5_KEY_PRE + url);
        BigInteger md5num = new BigInteger(sMD5EncryptResult, 16);//长度为39的一串数，可生成长度为21的string
        BigInteger scale = new BigInteger("62");
        //System.out.println(System.nanoTime() - start);
        //start = System.nanoTime();
        for (int i = 0; i < SHORT_URL_LEN; ++i) {
            resCode = DIGITS[md5num.mod(scale).intValue()] + resCode;
            md5num = md5num.divide(scale);
        }
        //System.out.println(System.nanoTime() - start);
        return "http://d-j.me/" + resCode;
    }

    public static void main(String[] args) {

        String sLongUrl = "http://dajie.com/";
        String aResult = shortUrlForMD5(sLongUrl);
        long start = System.nanoTime();
        System.out.println(aResult);
        System.out.println(System.nanoTime() - start);
        start = System.nanoTime();
        String bResult = generateShortUrl(sLongUrl);
        System.out.println(System.nanoTime() - start);
        System.out.println(bResult);
        System.out.println(3*0.1 ==0.3 );
    }
}