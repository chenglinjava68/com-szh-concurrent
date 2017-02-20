package com.szh.util.common;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.lang.StringUtils;

public class EmojiUtils {
    public EmojiUtils() {
    }

    public static String emojiFilter(String text) {
        if(text == null) {
            return "";
        } else {
            byte[] textBytes = text.getBytes(Charset.forName("UTF-8"));
            StringBuilder sb = new StringBuilder();
            String stmp = "";

            for(int n = 0; n < textBytes.length; ++n) {
                if((textBytes[n] & 255) >= 252 && (textBytes[n] & 255) < 254 && n < textBytes.length - 5) {
                    stmp = new String(textBytes, n, 6, Charset.forName("UTF-8"));
                    n += 5;
                } else if((textBytes[n] & 255) >= 248 && (textBytes[n] & 255) < 252 && n < textBytes.length - 4) {
                    stmp = new String(textBytes, n, 5, Charset.forName("UTF-8"));
                    n += 4;
                } else if((textBytes[n] & 255) >= 240 && (textBytes[n] & 255) < 248 && n < textBytes.length - 3) {
                    stmp = convertToString(textBytes[n], textBytes[n + 1], textBytes[n + 2], textBytes[n + 3]);
                    n += 3;
                } else if((textBytes[n] & 255) >= 224 && (textBytes[n] & 255) < 240 && n < textBytes.length - 2) {
                    stmp = new String(textBytes, n, 3, Charset.forName("UTF-8"));
                    n += 2;
                } else if((textBytes[n] & 255) >= 192 && (textBytes[n] & 255) < 224 && n < textBytes.length - 1) {
                    stmp = new String(textBytes, n, 2, Charset.forName("UTF-8"));
                    ++n;
                } else {
                    if((textBytes[n] & 255) >= 128) {
                        continue;
                    }

                    stmp = new String(textBytes, n, 1, Charset.forName("UTF-8"));
                }

                sb.append(stmp);
            }

            return sb.toString();
        }
    }

    public static String recoverToEmoji(String encodeText) throws PatternSyntaxException {
        if(encodeText == null) {
            return "";
        } else {
            String regEx = "(\\\\:)([0-9a-fA-F]{8})";
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(encodeText);
            StringBuffer sb = new StringBuffer();

            while(matcher.find()) {
                String temp = matcher.group(2);
                temp = new String(hexStringToBytes(temp), Charset.forName("UTF-8"));
                matcher.appendReplacement(sb, temp);
            }

            matcher.appendTail(sb);
            return sb.toString();
        }
    }

    public static boolean containsEmoji(String text) {
        if(text == null) {
            return false;
        } else {
            byte[] textBytes = text.getBytes(Charset.forName("UTF-8"));

            for(int n = 0; n < textBytes.length; ++n) {
                if((textBytes[n] & 255) >= 252 && (textBytes[n] & 255) < 254 && n < textBytes.length - 5) {
                    n += 5;
                    return true;
                }

                if((textBytes[n] & 255) >= 248 && (textBytes[n] & 255) < 252 && n < textBytes.length - 4) {
                    n += 4;
                    return true;
                }

                if((textBytes[n] & 255) >= 240 && (textBytes[n] & 255) < 248 && n < textBytes.length - 3) {
                    n += 3;
                    return true;
                }

                if((textBytes[n] & 255) >= 224 && (textBytes[n] & 255) < 240 && n < textBytes.length - 2) {
                    n += 2;
                } else if((textBytes[n] & 255) >= 192 && (textBytes[n] & 255) < 224 && n < textBytes.length - 1) {
                    ++n;
                } else if((textBytes[n] & 255) < 128) {
                    ;
                }
            }

            return false;
        }
    }

    public static String killEmoji(String source) {
        if(StringUtils.isBlank(source)) {
            return source;
        } else {
            StringBuilder sb = new StringBuilder();
            char[] var2 = source.toCharArray();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                char c = var2[var4];
                if(c == 0 || c == 9 || c == 10 || c == 13 || c >= 32 && c <= '\ud7ff' || c >= '\ue000' && c <= '�' || c >= 65536 && c <= 1114111) {
                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }

    private static byte[] hexStringToBytes(String hexString) {
        if(hexString != null && !hexString.equals("")) {
            hexString = hexString.toUpperCase();
            int length = hexString.length() / 2;
            char[] hexChars = hexString.toCharArray();
            byte[] d = new byte[length];

            for(int i = 0; i < length; ++i) {
                int pos = i * 2;
                d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
            }

            return d;
        } else {
            return null;
        }
    }

    private static byte charToByte(char c) {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }

    private static String convertToString(byte a, byte b, byte c, byte d) {
        StringBuilder sb = new StringBuilder("\\:");
        sb.append(Integer.toHexString(a & 255)).append(Integer.toHexString(b & 255)).append(Integer.toHexString(c & 255)).append(Integer.toHexString(d & 255));
        return sb.toString();
    }
}
