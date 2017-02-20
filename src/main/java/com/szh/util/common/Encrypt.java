package com.szh.util.common;

import java.net.URLDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Encrypt {
    private static final int CKEY_LENGTH = 4;
    private static Logger log = LoggerFactory.getLogger(Encrypt.class);

    public Encrypt() {
    }

    public static String decode(String code, String key) {
        if(code != null && code.length() != 0 && !"deleted".equals(code) && !"null".equals(code)) {
            try {
                if(code.indexOf(37) > -1) {
                    code = URLDecoder.decode(code, "UTF-8");
                }

                return _decode(code, key);
            } catch (Exception var3) {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String encodeWithInsensitive(String code, String key) throws Exception {
        String encode = encode(code, key);
        byte[] encodeBytes = encode.getBytes("UTF-8");
        return StringUtil.byte2HexString(encodeBytes).toLowerCase();
    }

    public static String decodeWithInsensitive(String code, String key) throws Exception {
        String hexString = code.toLowerCase();
        return hexString.length() % 2 == 0 && hexString.matches("[0-9a-f]+")?decode(new String(StringUtil.hexString2Bytes(code), "UTF-8"), key):"";
    }

    public static String encode(String code, String key) {
        try {
            return _encode(code, key, 0);
        } catch (Exception var3) {
            log.error("code encode error:" + code, var3);
            return "";
        }
    }

    public static String encode(String code, String key, int expiry) {
        try {
            return _encode(code, key, expiry);
        } catch (Exception var4) {
            log.error("code encode error:" + code, var4);
            return "";
        }
    }

    private static String _decode(String code, String key) throws Exception {
        if(key == null) {
            return "";
        } else {
            key = StringUtil.md5(key);
            String keya = StringUtil.md5(key.substring(0, 16));
            String keyb = StringUtil.md5(key.substring(16).substring(0, 16));
            String keyc = code.substring(0, 4);
            String cryptkey = keya + StringUtil.md5(keya + keyc);
            int key_length = cryptkey.length();
            byte[] codebyte = Base64.decode(code.substring(4));
            int codebytelen = codebyte.length;
            String result = "";
            int[] box = new int[256];

            for(int rndkey = 0; rndkey < box.length; box[rndkey] = rndkey++) {
                ;
            }

            int[] var17 = new int[256];

            int j;
            for(j = 0; j <= 255; ++j) {
                var17[j] = cryptkey.charAt(j % key_length);
            }

            j = 0;

            int timeCode;
            int authCode1;
            for(timeCode = 0; timeCode < 256; ++timeCode) {
                j = (j + box[timeCode] + var17[timeCode]) % 256;
                authCode1 = box[timeCode];
                box[timeCode] = box[j];
                box[j] = authCode1;
            }

            j = 0;
            int k = 0;

            for(timeCode = 0; timeCode < codebytelen; ++timeCode) {
                k = (k + 1) % 256;
                j = (j + box[k]) % 256;
                authCode1 = box[k];
                box[k] = box[j];
                box[j] = authCode1;
                result = result + (char)(codebyte[timeCode] & 255 ^ box[(box[k] + box[j]) % 256]);
            }

            timeCode = Integer.parseInt(result.substring(0, 10));
            String var18 = result.substring(10, 26);
            String authCode2 = StringUtil.md5(result.substring(26) + keyb).substring(0, 16);
            return (timeCode == 0 || (long)timeCode - System.currentTimeMillis() / 1000L > 0L) && var18.equals(authCode2)?result.substring(26):"";
        }
    }

    private static String _encode(String code, String key, int expiry) throws Exception {
        if(key == null) {
            return "";
        } else {
            key = StringUtil.md5(key);
            String keya = StringUtil.md5(key.substring(0, 16));
            String keyb = StringUtil.md5(key.substring(16).substring(0, 16));
            String keyc = StringUtil.md5(String.valueOf(System.currentTimeMillis()));
            keyc = keyc.substring(keyc.length() - 4);
            String cryptkey = keya + StringUtil.md5(keya + keyc);
            int key_length = cryptkey.length();
            String time = "0000000000";
            if(expiry > 0) {
                time = String.valueOf(System.currentTimeMillis() / 1000L + (long)expiry);
            }

            String codebyte = time + StringUtil.md5(code + keyb).substring(0, 16) + code;
            int codebytelen = codebyte.length();
            int[] box = new int[256];

            for(int rndkey = 0; rndkey < box.length; box[rndkey] = rndkey++) {
                ;
            }

            int[] var18 = new int[256];

            int j;
            for(j = 0; j <= 255; ++j) {
                var18[j] = cryptkey.charAt(j % key_length);
            }

            j = 0;

            int i;
            for(int result = 0; result < 256; ++result) {
                j = (j + box[result] + var18[result]) % 256;
                i = box[result];
                box[result] = box[j];
                box[j] = i;
            }

            j = 0;
            int k = 0;
            byte[] var19 = new byte[codebytelen];

            for(i = 0; i < codebytelen; ++i) {
                k = (k + 1) % 256;
                j = (j + box[k]) % 256;
                int tmp = box[k];
                box[k] = box[j];
                box[j] = tmp;
                var19[i] = (byte)(codebyte.charAt(i) ^ box[(box[k] + box[j]) % 256]);
            }

            return keyc + Base64.encode(var19).replaceAll("=", "");
        }
    }
}
