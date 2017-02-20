package com.szh.util.common;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DesEncryptUtils {
    private static final Logger LOG = LoggerFactory.getLogger(DesEncryptUtils.class);

    public DesEncryptUtils() {
    }

    public static byte[] desEncrypt(byte[] plainBytes, byte[] keyBytes) {
        return crypt(plainBytes, 1, keyBytes);
    }

    public static byte[] desDecrypt(byte[] cipherBytes, byte[] keyBytes) {
        return crypt(cipherBytes, 2, keyBytes);
    }

    public static byte[] crypt(byte[] text, int mode, byte[] keyBytes) {
        SecureRandom sr = new SecureRandom();
        byte[] cryptBytes = null;

        try {
            DESKeySpec e = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(e);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(mode, key, sr);
            cryptBytes = cipher.doFinal(text);
        } catch (Exception var10) {
            LOG.error("error occur!error msg:{}", var10.getMessage(), var10);
        }

        return cryptBytes;
    }

    public static String encrypt(String plainText, String key) {
        byte[] cipherBytes = null;

        try {
            cipherBytes = desEncrypt(plainText.getBytes(), key.getBytes());
        } catch (Exception var4) {
            LOG.error("error occur!error msg:{}", var4.getMessage(), var4);
        }

        return cipherBytes != null?base64Encode(cipherBytes):"";
    }

    public static String decrypt(String cipherText, String key) {
        byte[] decode = base64Decode(cipherText);
        String plainText = "";

        try {
            plainText = new String(desDecrypt(decode, key.getBytes()));
        } catch (Exception var5) {
            LOG.error("error occur!error msg:{}", var5.getMessage(), var5);
        }

        return plainText;
    }

    public static String base64Encode(byte[] s) {
        if(s == null) {
            return null;
        } else {
            BASE64Encoder b = new BASE64Encoder();
            return b.encode(s);
        }
    }

    public static byte[] base64Decode(String s) {
        if(s == null) {
            return null;
        } else {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];

            try {
                b = decoder.decodeBuffer(s);
            } catch (IOException var4) {
                LOG.error("error occur!error msg:{}", var4.getMessage(), var4);
            }

            return b;
        }
    }

    public static void main(String[] args) throws Exception {
        String key = "aaaaeactf";
        String input = "group1/M00/09/13/CgogqlE3JAaASLgdAAAFm5OC7Ag939.txt";
        String cipherText = encrypt(input, key);
        System.out.println("Encode:" + cipherText);
        System.out.println("Decode:" + decrypt(cipherText, key));
        System.out.print(URLEncoder.encode(cipherText, "UTF-8"));
    }
}
