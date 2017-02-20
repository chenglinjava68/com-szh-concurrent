package com.szh.util.common;

import com.sun.crypto.provider.SunJCE;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DesEncryptUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DesEncryptUtil.class);
    private static SecretKey deskey;
    private static Cipher c;

    public DesEncryptUtil() {
    }

    public static String encrypt(String key, String plainText, String charSet) {
        byte[] keyBytes = getKeyBytes(key);
        if(keyBytes == null) {
            return "";
        } else {
            if(charSet == null) {
                charSet = "utf-8";
            }

            preEncrypt(keyBytes);

            try {
                c.init(1, deskey);
                byte[] e = plainText.getBytes(charSet);
                byte[] enc = c.doFinal(e);
                return bytesToStr(enc);
            } catch (Exception var6) {
                LOGGER.error("{}", var6);
                return "";
            }
        }
    }

    public static String encrypt(String key, String plainText) {
        return encrypt(key, plainText, (String)null);
    }

    public static String decrypt(String key, String hexString) {
        byte[] keyBytes = getKeyBytes(key);
        preEncrypt(keyBytes);

        try {
            byte[] e = StringUtil.hexString2Bytes(hexString);
            c.init(2, deskey);
            byte[] dec = c.doFinal(e);
            return new String(dec);
        } catch (Exception var5) {
            LOGGER.error("{}", var5);
            return "";
        }
    }

    private static boolean preEncrypt(byte[] keyBytes) {
        try {
            DESKeySpec.isParityAdjusted(keyBytes, 0);
            deskey = new SecretKeySpec(keyBytes, "DES");
            c = Cipher.getInstance("DES");
            Security.addProvider(new SunJCE());
            return true;
        } catch (Exception var2) {
            LOGGER.error("{}", var2);
            return false;
        }
    }

    private static byte[] getKeyBytes(String key) {
        if(key != null && key.matches("[0-9a-zA-Z]{7}")) {
            byte[] result = key.getBytes();
            result = addParity(result);
            return result;
        } else {
            return null;
        }
    }

    private static String bytesToStr(byte[] bytes) {
        String result = "";
        byte[] var3 = bytes;
        int var4 = bytes.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            byte aByte = var3[var5];
            String temp = Integer.toHexString(aByte & 255);
            if(temp.length() == 1) {
                temp = "0" + temp;
            }

            result = result + temp;
        }

        return result;
    }

    public static byte[] addParity(byte[] in) {
        byte[] result = new byte[8];
        int resultIx = 1;
        int bitCount = 0;

        for(int i = 0; i < 56; ++i) {
            boolean bit = (in[6 - i / 8] & 1 << i % 8) > 0;
            if(bit) {
                result[7 - resultIx / 8] = (byte)(result[7 - resultIx / 8] | 1 << resultIx % 8 & 255);
                ++bitCount;
            }

            if((i + 1) % 7 == 0) {
                if(bitCount % 2 == 0) {
                    result[7 - resultIx / 8] = (byte)(result[7 - resultIx / 8] | 1);
                }

                ++resultIx;
                bitCount = 0;
            }

            ++resultIx;
        }

        return result;
    }
}