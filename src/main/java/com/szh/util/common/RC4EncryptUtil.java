package com.szh.util.common;


public final class RC4EncryptUtil {
    private static final int STATE_LENGTH = 256;

    private RC4EncryptUtil() {
    }

    public static String encrypt(String plainText, String key) {
        try {
            return Base64.encode(encrypt(key.getBytes("UTF-8"), plainText.getBytes("UTF-8"))).replaceAll("/", "_").replaceAll("\\+", "-").replaceAll("=", "*");
        } catch (Exception var3) {
            var3.printStackTrace();
            return "";
        }
    }

    public static byte[] encrypt(byte[] key, byte[] plainData) {
        int[] intKey = fromByteArray(key);
        int[] stateBox = new int[256];
        int[] keyBox = new int[256];

        int i;
        for(i = 0; i < 256; ++i) {
            stateBox[i] = i;
            keyBox[i] = intKey[i % key.length];
        }

        i = 0;

        int j;
        int temp;
        for(j = 0; i < 256; ++i) {
            j = (j + stateBox[i] + keyBox[i]) % 256;
            temp = stateBox[i];
            stateBox[i] = stateBox[j];
            stateBox[j] = temp;
        }

        i = 0;
        j = 0;
        byte[] cipher = new byte[plainData.length];

        for(int k = 0; k < plainData.length; ++k) {
            i = (i + 1) % 256;
            j = (j + stateBox[i]) % 256;
            temp = stateBox[i];
            stateBox[i] = stateBox[j];
            stateBox[j] = temp;
            int tempKey = stateBox[(stateBox[i] + stateBox[j]) % 256];
            cipher[k] = (byte)(tempKey ^ plainData[k]);
        }

        return cipher;
    }

    public static String decrypt(String cipherText, String key) {
        try {
            return new String(decrypt(key.getBytes("UTF-8"), Base64.decode(cipherText.replaceAll("_", "/").replaceAll("-", "+").replaceAll("\\*", "="))), "UTF-8");
        } catch (Exception var3) {
            return null;
        }
    }

    public static byte[] decrypt(byte[] key, byte[] cipherData) {
        return encrypt(key, cipherData);
    }

    private static int[] fromByteArray(byte[] array) {
        int[] intArray = new int[array.length];

        for(int i = 0; i < array.length; ++i) {
            intArray[i] = array[i] >= 0?array[i]:array[i] + 256;
        }

        return intArray;
    }
}
