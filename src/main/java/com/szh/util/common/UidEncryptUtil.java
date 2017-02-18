package com.szh.util.common;

import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class UidEncryptUtil {
    private static final String UID_ENCODE_KEY = "asdfaerqwerqerq";
    private static final char[] base90 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '!', '#', '$', '%', '&', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', ']', '^', '_', '{', '|', '}', '~'};
    private static final byte[] IV = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

    public UidEncryptUtil() {
    }

    public static int decryptUid(String cipherData) {
        try {
            SecretKeySpec e = new SecretKeySpec(toKey("asdfaerqwerqerq"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            IvParameterSpec initialParameter = new IvParameterSpec(IV);
            cipher.init(2, e, initialParameter);
            byte[] b = cipher.doFinal(StringUtil.hexString2Bytes(cipherData));
            String decryptData = new String(b, "UTF-8");
            char checkCode = decryptData.charAt(0);
            String data = decryptData.substring(1);
            char[] ca = data.toCharArray();
            int result = 0;
            String s = new String(base90);

            for(int i = 0; i < ca.length; ++i) {
                result = (int)((double)result + Math.pow(90.0D, (double)(ca.length - i - 1)) * (double)s.indexOf(ca[i]));
            }

            return base90[result % base90.length] != checkCode?0:result;
        } catch (Exception var12) {
            return -1;
        }
    }

    public static String encryptUid(int uid) {
        try {
            byte[] ignored = (base90[uid % base90.length] + toBase90(uid)).getBytes("UTF-8");

            try {
                SecretKeySpec e = new SecretKeySpec(toKey("asdfaerqwerqerq"), "AES");
                Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
                IvParameterSpec initialParameter = new IvParameterSpec(IV);
                cipher.init(1, e, initialParameter);
                byte[] b = cipher.doFinal(ignored);
                return StringUtil.byte2HexString(b);
            } catch (Exception var6) {
                return null;
            }
        } catch (Exception var7) {
            return null;
        }
    }

    private static String toBase90(int i) {
        int tmp = i;
        StringBuilder result = new StringBuilder();

        do {
            int y = tmp % 90;
            result.insert(0, base90[y]);
            tmp = (tmp - y) / 90;
        } while(tmp >= 90);

        result.insert(0, base90[tmp]);
        return result.toString();
    }

    private static byte[] toKey(String toBeHashedString) {
        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            e.reset();
            e.update(toBeHashedString.getBytes());
            return e.digest();
        } catch (Exception var2) {
            return null;
        }
    }

    public static void main(String[] args) {
        String encryptUid = encryptUid(107146413);

        assert decryptUid(encryptUid) == 107146413;

    }
}
