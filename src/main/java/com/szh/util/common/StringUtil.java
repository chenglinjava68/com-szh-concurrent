package com.szh.util.common;

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
    private static final int MAX_CHAR_CODE = 255;
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^\\d{11}$");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9])|(14[5,7]))\\d{8}$");

    public StringUtil() {
    }

    public static int charCount(String s) {
        if(s != null && !s.isEmpty()) {
            int count = s.length();

            for(int i = 0; i < s.length(); ++i) {
                if(s.charAt(i) > 255) {
                    ++count;
                }
            }

            return count;
        } else {
            return 0;
        }
    }

    public static String md5(String s) {
        try {
            MessageDigest e = MessageDigest.getInstance("md5");
            e.reset();
            e.update(s.getBytes());
            byte[] encodedStr = e.digest();
            StringBuilder buf = new StringBuilder();
            byte[] var4 = encodedStr;
            int var5 = encodedStr.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                byte anEncodedStr = var4[var6];
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
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                Object aList = var4.next();
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
                String[] var5 = items;
                int var6 = items.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    String item = var5[var7];
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

    public static boolean isPhoneNumber(String phone) {
        return isMobileNO(phone);
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
        byte[] var2 = bytes;
        int var3 = bytes.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte aByte = var2[var4];
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

    public static boolean isNumeric(String s) {
        if(s != null && !s.equals("")) {
            int i = s.length();

            do {
                --i;
                if(i < 0) {
                    return true;
                }
            } while(Character.isDigit(s.charAt(i)));

            return false;
        } else {
            return false;
        }
    }

    /** @deprecated */
    @Deprecated
    public static int valueOf(String s) {
        return !isNumeric(s)?0:Integer.valueOf(s).intValue();
    }

    public static String capitalize(String srcString) {
        if(srcString != null && !srcString.isEmpty()) {
            String firstChar = srcString.substring(0, 1);
            return firstChar.toUpperCase() + (srcString.length() > 1?srcString.substring(1):"");
        } else {
            return srcString;
        }
    }

    public static String jsEscape(String str) {
        if(str == null) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer(str.length() + str.length() / 2);

            for(int i = 0; i < str.length(); ++i) {
                char chr = str.charAt(i);
                if(chr == 39) {
                    sb.append("\\\'");
                } else if(chr == 34) {
                    sb.append("\\\"");
                } else if(chr == 38) {
                    sb.append("\\&");
                } else if(chr == 92) {
                    sb.append("\\\\");
                } else if(chr == 10) {
                    sb.append("\\n");
                } else if(chr == 13) {
                    sb.append("\\r");
                } else if(chr == 9) {
                    sb.append("\\t");
                } else if(chr == 8) {
                    sb.append("\\b");
                } else if(chr == 12) {
                    sb.append("\\f");
                } else {
                    sb.append(chr);
                }
            }

            return sb.toString();
        }
    }

    public static String breakword(String s, int maxlength) {
        StringBuffer sb = new StringBuffer();
        int start = -1;

        for(int i = 0; i < s.length(); ++i) {
            char ascCode = s.charAt(i);
            sb.append(s.charAt(i));
            if(ascCode < 128 && ascCode != 8 && ascCode != 9 && ascCode != 10 && ascCode != 13 && ascCode != 32) {
                if(start == -1) {
                    start = i;
                }

                if(i - start == maxlength - 1) {
                    sb.append(" ");
                    start = -1;
                }
            }
        }

        return sb.toString();
    }

    public static String getFormHash(Integer uid) {
        String code = (System.currentTimeMillis() + "").substring(0, 3) + "|" + uid + "|" + md5("65d45588GCIBoXdL") + "|";
        code = md5(code).substring(8, 16);
        return code;
    }

    /** @deprecated */
    @Deprecated
    public static String feedHtml(String content, String wwwDomain, String assetDomain, String target) {
        if(isEmpty(content)) {
            return "";
        } else if(isEmpty(wwwDomain)) {
            throw new IllegalArgumentException("please get value from config for domains.www");
        } else if(isEmpty(assetDomain)) {
            throw new IllegalArgumentException("please get value from config for domains.assets");
        } else {
            content = content.replaceAll("\r\n", " &nbsp;");
            content = content.replaceAll("\n", " &nbsp;");
            content = content.replaceAll("\\/\\/([[\\u4e00-\\u9fa5]*\\w]+)\\#(\\d+)\\#", "\\/\\/<a href=\"http://" + wwwDomain + "/profile/$2\" target=\"" + target + "\">$1</a>");
            return content.replaceAll("\\[em:(\\d+):\\]", "<img src=\"http://" + assetDomain + "/images/face/$1.gif\" />");
        }
    }

    /** @deprecated */
    @Deprecated
    public static String feedHtml(String content, String wwwDomain, String assetDomain) {
        return feedHtml(content, wwwDomain, assetDomain, "_self");
    }

    /** @deprecated */
    @Deprecated
    public static int feedLength(String content) {
        content = content.replaceAll("(^\\s*)|(\\s*$)", "").replaceAll("(^　*)|(　*$)", "").replaceAll("\\#\\d+\\#", "");
        int length = content.replaceAll("(https|http|ftp|rtsp|mms):\\/\\/[0-9a-zA-Z_\\%\\&\\?\\.\\+\\$/\\=\\-]+", "").length();
        String str = "((https|http|ftp|rtsp|mms)://[0-9a-zA-Z_\\%\\&\\?\\.\\+\\$/\\=\\-]+)";
        Pattern pattern = Pattern.compile(str, 2);
        Matcher matcher = pattern.matcher(content);
        ArrayList urls = new ArrayList();

        while(matcher.find()) {
            urls.add(matcher.group(0));
        }

        Iterator var6 = urls.iterator();

        while(var6.hasNext()) {
            String url = (String)var6.next();
            if(url.length() > 141) {
                length = length + url.length() - 141 + 22;
            } else {
                length += 22;
            }
        }

        return length;
    }

    /** @deprecated */
    @Deprecated
    public static String feedReplace(String content) {
        content = content.replaceAll("(^\\s*)|(\\s*$)", "").replaceAll("(^　*)|(　*$)", "");
        content = content.replace("<", "&lt;");
        content = content.replace(">", "&gt;");
        content = content.replace("\'", "&#39;");
        content = content.replace(" ", " &nbsp;");
        content = content.replaceAll("\r\n", " &nbsp;");
        content = content.replaceAll("\n", " &nbsp;");
        return content;
    }

    public static String transforURL(String html, String domainFilter, String domain) {
        return isEmpty(html)?html:html.replace("${configs[\'" + domainFilter + "\']}", domain);
    }

    public static boolean isMobileNO(String mobile) {
        if(isEmpty(mobile)) {
            return false;
        } else {
            Matcher m = MOBILE_PATTERN.matcher(mobile);
            return m.matches();
        }
    }

    public static boolean isChinese(String str) {
        if(isEmpty(str)) {
            return false;
        } else {
            Pattern p = Pattern.compile("^[\\u3400-\\u4DB5\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$");
            return p.matcher(str).matches();
        }
    }
}
