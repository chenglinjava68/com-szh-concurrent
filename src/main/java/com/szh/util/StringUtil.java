package com.szh.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class StringUtil extends StringUtils {
    private static final String[] HEX_DIGITS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public StringUtil() {
    }

    public static String md5(String s) {
        try {
            MessageDigest e = MessageDigest.getInstance("md5");
            e.reset();
            e.update(s.getBytes());
            byte[] encodedStr = e.digest();
            StringBuilder buf = new StringBuilder();
            byte[] arr$ = encodedStr;
            int len$ = encodedStr.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                byte anEncodedStr = arr$[i$];
                int n = anEncodedStr & 255;
                int d1 = n / 16;
                int d2 = n % 16;
                buf.append(HEX_DIGITS[d1]).append(HEX_DIGITS[d2]);
            }

            return buf.toString();
        } catch (NoSuchAlgorithmException var11) {
            throw new RuntimeException(var11);
        }
    }

    public static boolean isInteger(String s) {
        boolean isInteger = true;

        try {
            Integer.parseInt(s);
        } catch (Exception var3) {
            isInteger = false;
        }

        return isInteger;
    }

    public static boolean isLong(String s) {
        boolean isLong = true;

        try {
            Long.parseLong(s);
        } catch (Exception var3) {
            isLong = false;
        }

        return isLong;
    }

    public static String trim(String s) {
        return s == null?s:s.replaceAll("[ 　]+", "");
    }

    public static boolean isEmpty(String s) {
        return s == null || trim(s).length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static String join(List<?> list, String delimiter) {
        if(list != null && list.size() > 0) {
            if(delimiter == null || delimiter.isEmpty()) {
                delimiter = " ";
            }

            StringBuilder buffer = new StringBuilder();
            int count = 1;
            Iterator i$ = list.iterator();

            while(i$.hasNext()) {
                Object aList = i$.next();
                if(aList != null) {
                    if(count != 1) {
                        buffer.append(delimiter);
                    }

                    buffer.append(aList.toString());
                    ++count;
                }
            }

            return buffer.toString();
        } else {
            return "";
        }
    }

    public static String join(Object[] array, String delimiter) {
        List list;
        if(array != null && array.length != 0) {
            list = Arrays.asList(array);
        } else {
            list = Collections.emptyList();
        }

        return join(list, delimiter);
    }

    public static List<String> splitStringByDelimiter(String toBeSplit, String delimiter, String matchRegex) {
        ArrayList resultList = new ArrayList();
        if(toBeSplit != null && !toBeSplit.isEmpty()) {
            if(delimiter == null) {
                delimiter = "";
            }

            String[] items = toBeSplit.split(delimiter);
            if(matchRegex == null) {
                Collections.addAll(resultList, items);
            } else {
                String[] arr$ = items;
                int len$ = items.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    String item = arr$[i$];
                    if(item.matches(matchRegex)) {
                        resultList.add(item);
                    }
                }
            }
        }

        return resultList;
    }

    public static boolean isEmail(String email) {
        if(isEmpty(email)) {
            return false;
        } else {
            Pattern p = Pattern.compile("^[_a-z0-9\\.\\-]+@([\\._a-z0-9\\-]+\\.)+[a-z0-9]{2,4}$");
            Matcher m = p.matcher(email.toLowerCase());
            return m.matches();
        }
    }

    public static byte[] hexString2Bytes(String src) {
        int length = src.length() / 2;
        byte[] result = new byte[length];
        char[] array = src.toCharArray();

        for(int i = 0; i < length; ++i) {
            result[i] = uniteBytes(array[2 * i], array[2 * i + 1]);
        }

        return result;
    }

    public static String byte2HexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        byte[] arr$ = bytes;
        int len$ = bytes.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            byte aByte = arr$[i$];
            String hex = Integer.toHexString(255 & aByte);
            if(hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }

        return hexString.toString();
    }

    private static byte uniteBytes(char mostChar, char secondChar) {
        byte b0 = Byte.decode("0x" + String.valueOf(mostChar)).byteValue();
        b0 = (byte)(b0 << 4);
        byte b1 = Byte.decode("0x" + String.valueOf(secondChar)).byteValue();
        return (byte)(b0 | b1);
    }
}