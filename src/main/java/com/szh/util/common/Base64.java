package com.szh.util.common;

import java.util.Arrays;

public class Base64 {
    private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    private static byte[] codes = new byte[256];

    public Base64() {
    }

    public static String encodeWithDajieSpec(byte[] data) {
        return data != null && data.length != 0?encode(data).replaceAll("/", "_").replaceAll("\\+", "-").replaceAll("=", "*"):null;
    }

    public static byte[] decodeWithDajieSpec(String base64StringWithDajieSpec) {
        if(StringUtil.isEmpty(base64StringWithDajieSpec)) {
            return null;
        } else {
            try {
                return decode(base64StringWithDajieSpec.replaceAll("_", "/").replaceAll("-", "+").replaceAll("\\*", "="));
            } catch (Exception var2) {
                return null;
            }
        }
    }

    public static String encode(String dataStr) {
        byte[] data = dataStr.getBytes();
        char[] out = new char[(data.length + 2) / 3 * 4];
        int i = 0;

        for(int index = 0; i < data.length; index += 4) {
            boolean quad = false;
            boolean trip = false;
            int val = 255 & data[i];
            val <<= 8;
            if(i + 1 < data.length) {
                val |= 255 & data[i + 1];
                trip = true;
            }

            val <<= 8;
            if(i + 2 < data.length) {
                val |= 255 & data[i + 2];
                quad = true;
            }

            out[index + 3] = alphabet[quad?val & 63:64];
            val >>= 6;
            out[index + 2] = alphabet[trip?val & 63:64];
            val >>= 6;
            out[index + 1] = alphabet[val & 63];
            val >>= 6;
            out[index] = alphabet[val & 63];
            i += 3;
        }

        return new String(out);
    }

    public static String encode(byte[] data) {
        char[] out = new char[(data.length + 2) / 3 * 4];
        int i = 0;

        for(int index = 0; i < data.length; index += 4) {
            boolean quad = false;
            boolean trip = false;
            int val = 255 & data[i];
            val <<= 8;
            if(i + 1 < data.length) {
                val |= 255 & data[i + 1];
                trip = true;
            }

            val <<= 8;
            if(i + 2 < data.length) {
                val |= 255 & data[i + 2];
                quad = true;
            }

            out[index + 3] = alphabet[quad?val & 63:64];
            val >>= 6;
            out[index + 2] = alphabet[trip?val & 63:64];
            val >>= 6;
            out[index + 1] = alphabet[val & 63];
            val >>= 6;
            out[index] = alphabet[val & 63];
            i += 3;
        }

        return new String(out);
    }

    public static byte[] decode(String dataStr) throws Exception {
        char[] data = dataStr.toCharArray();
        int len = (data.length + 3) / 4 * 3;
        if(data.length > 0 && data[data.length - 1] == 61) {
            --len;
        }

        if(data.length > 1 && data[data.length - 2] == 61) {
            --len;
        }

        byte[] out = new byte[len];
        int shift = 0;
        int accum = 0;
        int index = 0;
        char[] var7 = data;
        int var8 = data.length;

        for(int var9 = 0; var9 < var8; ++var9) {
            char aData = var7[var9];
            byte value = codes[aData & 255];
            if(value >= 0) {
                accum <<= 6;
                shift += 6;
                accum |= value;
                if(shift >= 8) {
                    shift -= 8;
                    out[index++] = (byte)(accum >> shift & 255);
                }
            }
        }

        if(out.length > index) {
            out = Arrays.copyOfRange(out, 0, index);
        }

        if(index != out.length) {
            throw new Error("miscalculated data length!");
        } else {
            return out;
        }
    }

    static {
        int i;
        for(i = 0; i < 256; ++i) {
            codes[i] = -1;
        }

        for(i = 65; i <= 90; ++i) {
            codes[i] = (byte)(i - 65);
        }

        for(i = 97; i <= 122; ++i) {
            codes[i] = (byte)(26 + i - 97);
        }

        for(i = 48; i <= 57; ++i) {
            codes[i] = (byte)(52 + i - 48);
        }

        codes[43] = 62;
        codes[47] = 63;
    }
}
