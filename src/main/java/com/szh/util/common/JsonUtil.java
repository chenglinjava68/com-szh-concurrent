package com.szh.util.common;

/** @deprecated */
@Deprecated
public class JsonUtil {
    private static final String JSON_RESULT_FORMAT1 = "{\"result\":%s}";
    private static final String JSON_RESULT_FORMAT2 = "{\"result\":%s, \"message\":\"%s\"}";
    private static final String JSON_RESULT_FORMAT3 = "{\"result\":\"%s\"}";
    private static final String JSON_RESULT_FORMAT4 = "{\"result\":\"%s\", \"message\":\"%s\"}";

    public JsonUtil() {
    }

    public static String jsonResult(boolean result) {
        return String.format("{\"result\":%s}", new Object[]{Boolean.valueOf(result)});
    }

    public static String jsonResult(boolean result, String message) {
        return String.format("{\"result\":%s, \"message\":\"%s\"}", new Object[]{Boolean.valueOf(result), message == null?"":message});
    }

    public static String jsonResult(int result) {
        return String.format("{\"result\":%s}", new Object[]{Integer.valueOf(result)});
    }

    public static String jsonResult(int result, String message) {
        return String.format("{\"result\":%s, \"message\":\"%s\"}", new Object[]{Integer.valueOf(result), message == null?"":message});
    }

    public static String jsonResult(String result) {
        return String.format("{\"result\":\"%s\"}", new Object[]{result});
    }

    public static String jsonResult(String result, String message) {
        return String.format("{\"result\":\"%s\", \"message\":\"%s\"}", new Object[]{result, message == null?"":message});
    }
}
