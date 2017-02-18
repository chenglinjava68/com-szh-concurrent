package com.szh.util.common;

public class AjaxUtil {
    public AjaxUtil() {
    }

    public static String response(int code, String msg) {
        return code == 0?"0":code + ":" + msg;
    }
}