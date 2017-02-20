package com.szh.util.common;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.CRC32;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesCryptUtil {
    private static final byte[] IV = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private static final byte DEFAULT_MAX_CONFOUND_SIZE = 3;

    private AesCryptUtil() {
    }

    public static void main(String[] args) {
        String plainDataInEn = "I\'m a plain data";
        String plainDataInCh = "我是明文";
        String plainDataInMixLn = "I\'m a plain data, 我是明文";
        String emptyPlainData = "";
        String password = "!#$!@FADFQ#!";
        String emptyPassword = "";

        assert decrypt(encrypt(plainDataInEn, password), password).equals(plainDataInEn) && decrypt(encrypt(plainDataInEn, emptyPassword), emptyPassword).equals(plainDataInEn);

        assert decrypt(encrypt(plainDataInCh, password), password).equals(plainDataInCh) && decrypt(encrypt(plainDataInCh, emptyPassword), emptyPassword).equals(plainDataInCh);

        assert decrypt(encrypt(plainDataInMixLn, password), password).equals(plainDataInMixLn) && decrypt(encrypt(plainDataInMixLn, emptyPassword), emptyPassword).equals(plainDataInMixLn);

        assert decrypt(encrypt(emptyPlainData, password), password).equals(emptyPlainData) && decrypt(encrypt(emptyPlainData, emptyPassword), emptyPassword).equals(emptyPlainData);

        assert decryptWithoutConfound(encryptWithoutConfound(plainDataInEn, password), password).equals(plainDataInEn) && decryptWithoutConfound(encryptWithoutConfound(plainDataInEn, emptyPassword), emptyPassword).equals(plainDataInEn);

        assert decryptWithoutConfoundWithCaseInsensitive(encryptWithoutConfoundWithCaseInsensitive(plainDataInCh, password), password).equals(plainDataInCh) && decryptWithoutConfoundWithCaseInsensitive(encryptWithoutConfoundWithCaseInsensitive(plainDataInCh, emptyPassword), emptyPassword).equals(plainDataInCh);

        assert decryptWithCaseInsensitive(encryptWithCaseInsensitive(emptyPlainData, password), password).equals(emptyPlainData) && decryptWithCaseInsensitive(encryptWithCaseInsensitive(emptyPlainData, emptyPassword), emptyPassword).equals(emptyPlainData);
    }

    public static String encrypt(String plainData, String password) {
        return encrypt(plainData, password, false, 3);
    }

    public static String decrypt(String cipherData, String password) {
        return cipherData != null && cipherData.matches("[0-9a-zA-Z*_-]+")?decrypt(cipherData, password, false):null;
    }

    public static String encryptWithCaseInsensitive(String plainData, String password) {
        return encrypt(plainData, password, true, 3);
    }

    public static String decryptWithCaseInsensitive(String cipherData, String password) {
        return cipherData != null && cipherData.matches("[0-9a-fA-F]+")?decrypt(cipherData, password, true):null;
    }

    public static String encryptWithoutConfound(String plainData, String password) {
        return encrypt(plainData, password, false, 0);
    }

    public static String decryptWithoutConfound(String cipherData, String password) {
        return decrypt(cipherData, password, false);
    }

    public static String encryptWithoutConfoundWithCaseInsensitive(String plainData, String password) {
        return encrypt(plainData, password, true, 0);
    }

    public static String decryptWithoutConfoundWithCaseInsensitive(String cipherData, String password) {
        return decrypt(cipherData, password, true);
    }

    private static byte[] verifyChecksum(byte[] plainData) {
        if(plainData != null && plainData.length > 4) {
            byte[] checksum = new byte[4];
            byte[] srcData = new byte[plainData.length - checksum.length];
            System.arraycopy(plainData, 0, srcData, 0, srcData.length);
            System.arraycopy(plainData, srcData.length, checksum, 0, checksum.length);
            return isEqualByteArray(calculateChecksum(srcData), checksum)?srcData:null;
        } else {
            return null;
        }
    }

    private static boolean isEqualByteArray(byte[] array1, byte[] array2) {
        if(array1 != null && array2 != null) {
            boolean isEqual = true;

            for(int i = 0; i < array1.length; ++i) {
                isEqual = array1[i] == array2[i];
                if(!isEqual) {
                    break;
                }
            }

            return isEqual;
        } else {
            return array1 == null && array2 == null;
        }
    }

    private static byte[] calculateChecksum(byte[] plainData) {
        CRC32 crc32 = new CRC32();
        crc32.update(plainData);
        return toBytes(crc32.getValue());
    }

    private static byte[] toBytes(long input) {
        byte[] output = new byte[4];

        for(int i = 0; i < output.length; ++i) {
            output[i] = (byte)((int)(input >>> i * 8 & 255L));
        }

        return output;
    }

    private static byte[] combinePlainDataWithChecksum(byte[] plainData) {
        byte[] checksum = calculateChecksum(plainData);
        byte[] combineResult = new byte[plainData.length + checksum.length];
        System.arraycopy(plainData, 0, combineResult, 0, plainData.length);
        System.arraycopy(checksum, 0, combineResult, plainData.length, checksum.length);
        return combineResult;
    }

    private static byte[] mixPlainData(byte[] plainData, byte mixDataSize) {
        if(plainData == null) {
            return plainData;
        } else {
            byte[] toBeEncrypt = new byte[1 + mixDataSize + plainData.length];
            Random random = new Random();
            toBeEncrypt[0] = mixDataSize;

            for(int i = 0; i < toBeEncrypt[0]; ++i) {
                toBeEncrypt[i + 1] = (byte)Math.abs(random.nextInt() % 127);
            }

            System.arraycopy(plainData, 0, toBeEncrypt, 1 + toBeEncrypt[0], plainData.length);
            return toBeEncrypt;
        }
    }

    private static byte[] retrieveToBeDecryptData(byte[] cipherData) {
        if(cipherData == null) {
            return cipherData;
        } else if(cipherData[0] >= 0 && cipherData[0] < cipherData.length) {
            byte[] var6 = new byte[cipherData.length - cipherData[0] - 1];
            System.arraycopy(cipherData, 1 + cipherData[0], var6, 0, var6.length);
            return var6;
        } else {
            ArrayList toBeDecryptData = new ArrayList();
            byte[] var2 = cipherData;
            int var3 = cipherData.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                byte aCipherData = var2[var4];
                toBeDecryptData.add(Byte.valueOf(aCipherData));
            }

            throw new IllegalArgumentException("Illegal confound size,maybe there is an error when decrypt.The decrypted data is " + StringUtil.join(toBeDecryptData, ","));
        }
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

    private static byte[] aesHandler(byte[] toBeHandleData, String password, boolean isEncrypt) {
        try {
            SecretKeySpec e = new SecretKeySpec(toKey(password), "AES");
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            IvParameterSpec initialParameter = new IvParameterSpec(IV);
            cipher.init(isEncrypt?1:2, e, initialParameter);
            return cipher.doFinal(toBeHandleData);
        } catch (Exception var6) {
            return null;
        }
    }

    private static String encrypt(String plainData, String password, boolean caseInsensitive, int confoundSize) {
        if(plainData == null) {
            return null;
        } else {
            byte[] cipherDataArray = null;

            try {
                Random ignored = new Random();
                byte[] plainDataArray = plainData.getBytes("UTF-8");
                byte fixedConfoundSize = confoundSize <= 0?0:(byte)(Math.abs(ignored.nextInt() % confoundSize) + 1);
                cipherDataArray = encrypt(combinePlainDataWithChecksum(mixPlainData(plainDataArray, fixedConfoundSize)), password);
            } catch (Exception var8) {
                ;
            }

            return cipherDataArray != null && cipherDataArray.length != 0?(caseInsensitive?StringUtil.byte2HexString(cipherDataArray):Base64.encodeWithDajieSpec(cipherDataArray)):null;
        }
    }

    private static String decrypt(String cipherData, String password, boolean caseInsensitive) {
        if(cipherData == null) {
            return null;
        } else {
            byte[] cipherDataArray = caseInsensitive?StringUtil.hexString2Bytes(cipherData):Base64.decodeWithDajieSpec(cipherData);
            byte[] plainDataArray = verifyChecksum(decrypt(cipherDataArray, password));
            if(plainDataArray != null && plainDataArray.length != 0) {
                String result = null;

                try {
                    result = new String(retrieveToBeDecryptData(plainDataArray), "UTF-8");
                } catch (Exception var7) {
                    var7.printStackTrace();
                }

                return result;
            } else {
                return null;
            }
        }
    }

    private static byte[] encrypt(byte[] plainData, String password) {
        if(plainData == null) {
            return plainData;
        } else {
            if(password == null) {
                password = "";
            }

            return aesHandler(plainData, password, true);
        }
    }

    private static byte[] decrypt(byte[] cipherData, String password) {
        if(cipherData != null && cipherData.length != 0) {
            if(password == null) {
                password = "";
            }

            return aesHandler(cipherData, password, false);
        } else {
            return cipherData;
        }
    }
}
