package com.szh.util.common;

import java.net.URLEncoder;

public class UrlEncoderUtil {
    private UrlEncoderUtil() {
    }

    public static String encode(String toBeEncoded, String charSet, String defaultReturnValue) {
        String encodedString;
        try {
            encodedString = URLEncoder.encode(toBeEncoded, charSet);
        } catch (Exception var5) {
            System.err.println(String.format("Error when url-encode:%s with charset:%s", new Object[]{toBeEncoded == null?"":toBeEncoded, charSet == null?"":charSet}));
            var5.printStackTrace();
            encodedString = defaultReturnValue;
        }

        return encodedString;
    }

    public static String encodeByUtf8(String toBeEncoded, String defaultReturnValue) {
        return encode(toBeEncoded, "utf-8", defaultReturnValue);
    }

    public static String encodeByUtf8(String toBeEncoded) {
        return encodeByUtf8(toBeEncoded, (String)null);
    }
}
