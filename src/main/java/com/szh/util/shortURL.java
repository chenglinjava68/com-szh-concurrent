package com.szh.util;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

import static com.alibaba.fastjson.util.IOUtils.DIGITS;
import static java.sql.Types.BINARY;

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
        System.out.println(chars.length + "\t");
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
            System.out.println(sMD5EncryptResult + "\t" + sMD5EncryptResult.length());

            BigInteger md5num = new BigInteger(sMD5EncryptResult, 16);
            System.out.println(md5num + "\t" + md5num.bitLength());

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

    public static String[] shortUrl(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "";
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"};

        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = DigestUtils.md5Hex(key + url);
        String hex = sMD5EncryptResult;
        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用
            // long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }

            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl;
    }

    public static String[] shortUrl_2(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "libotao";
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
        };
        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = (DigestUtils.md5Hex(key + url));
        String hex = sMD5EncryptResult;
        String[] resUrl = new String[4];
        //得到 4组短链接字符串
        for (int i = 0; i < 4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            //循环获得每组6位的字符串
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
//(具体需要看chars数组的长度   以防下标溢出，注意起点为0)
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl;
    }


    public static String[] shortUrl_3(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "test";
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"

        };
        // 对传入网址进行 MD5 加密
        String hex = DigestUtils.md5Hex(key + url);

        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {

            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);

            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl;
    }

    static String key = "zhihaosong";

    private static String[] generateShortUrl(String url) {
        String sMD5EncryptResult = DigestUtils.md5Hex(key + url);
        String hex = sMD5EncryptResult;
        String[] resUrl = new String[4];

        for (int i = 0; i < 4; i++) {
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                long index = 0x0000003D & lHexLong;
                outChars += chars[(int) index];
                lHexLong = lHexLong >> 5;
            }
            resUrl[i] = outChars;
        }
        return resUrl;
    }

    private AtomicLong sequence = new AtomicLong(0);

    protected String shorten(String longUrl) {
        long myseq = sequence.incrementAndGet();
        String shortUrl = to62RadixString(myseq);
        return shortUrl;
    }

    private String to62RadixString(long seq) {
        StringBuilder sBuilder = new StringBuilder();
        while (true) {
            int remainder = (int) (seq % 62);
            sBuilder.append(DIGITS[remainder]);
            seq = seq / 62;
            if (seq == 0) {
                break;
            }
        }
        return sBuilder.toString();
    }

    public String shorten(String longUrl, int urlLength) {
        if (urlLength < 0 || urlLength > 6) {
            throw new IllegalArgumentException("the length of url must be between 0 and 6");
        }
        String md5Hex = DigestUtils.md5Hex(longUrl);
        // 6 digit binary can indicate 62 letter & number from 0-9a-zA-Z
        int binaryLength = urlLength * 6;
        long binaryLengthFixer = Long.valueOf(StringUtils.repeat("1", binaryLength), BINARY);
        for (int i = 0; i < 4; i++) {
            String subString = StringUtils.substring(md5Hex, i * 8, (i + 1) * 8);
            subString = Long.toBinaryString(Long.valueOf(subString, 16) & binaryLengthFixer);
            subString = StringUtils.leftPad(subString, binaryLength, "0");
            StringBuilder sbBuilder = new StringBuilder();
            for (int j = 0; j < urlLength; j++) {
                String subString2 = StringUtils.substring(subString, j * 6, (j + 1) * 6);
                int charIndex = Integer.valueOf(subString2, BINARY) & 61;
                sbBuilder.append(DIGITS[charIndex]);
            }
            String shortUrl = sbBuilder.toString();
            return shortUrl;
        }
        // if all 4 possibilities are already exists
        return null;
    }
}
