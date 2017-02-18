package com.szh.util.common;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

public class JsonResultUtil {
    private static final Gson GSON = new Gson();

    public JsonResultUtil() {
    }

    public static String jsonResult(boolean result) {
        HashMap params = new HashMap();
        params.put("result", Integer.valueOf(result?0:1));
        return GSON.toJson(params);
    }

    public static String jsonResult(int result) {
        HashMap params = new HashMap();
        params.put("result", Integer.valueOf(result));
        return GSON.toJson(params);
    }

    public static String jsonResult(boolean result, String message) {
        HashMap params = new HashMap();
        params.put("result", Integer.valueOf(result?0:1));
        if(message != null) {
            params.put("message", message);
        }

        return GSON.toJson(params);
    }

    public static String jsonResult(int result, String message) {
        HashMap params = new HashMap();
        params.put("result", Integer.valueOf(result));
        if(message != null) {
            params.put("message", message);
        }

        return GSON.toJson(params);
    }

    public static String jsonResult(boolean result, Map<String, Object> data) {
        HashMap params = new HashMap();
        params.put("result", Integer.valueOf(result?0:1));
        if(data != null) {
            params.put("data", data);
        }

        return GSON.toJson(params);
    }

    public static String jsonResult(int result, Map<String, Object> data) {
        HashMap params = new HashMap();
        params.put("result", Integer.valueOf(result));
        if(data != null) {
            params.put("data", data);
        }

        return GSON.toJson(params);
    }

    public static String jsonResult(boolean result, String message, Map<String, Object> data) {
        HashMap params = new HashMap();
        params.put("result", Integer.valueOf(result?0:1));
        if(message != null) {
            params.put("message", message);
        }

        if(data != null) {
            params.put("data", data);
        }

        return GSON.toJson(params);
    }

    public static String jsonResult(int result, String message, Map<String, Object> data) {
        HashMap params = new HashMap();
        params.put("result", Integer.valueOf(result));
        if(message != null) {
            params.put("message", message);
        }

        if(data != null) {
            params.put("data", data);
        }

        return GSON.toJson(params);
    }

    public static String jsonPResult(String callback, boolean result) {
        return toJsonP(callback, jsonResult(result));
    }

    public static String jsonPResult(String callback, int result) {
        return toJsonP(callback, jsonResult(result));
    }

    public static String jsonPResult(String callback, boolean result, String message) {
        return toJsonP(callback, jsonResult(result, message));
    }

    public static String jsonPResult(String callback, int result, String message) {
        return toJsonP(callback, jsonResult(result, message));
    }

    public static String jsonPResult(String callback, boolean result, Map<String, Object> data) {
        return toJsonP(callback, jsonResult(result, data));
    }

    public static String jsonPResult(String callback, int result, Map<String, Object> data) {
        return toJsonP(callback, jsonResult(result, data));
    }

    public static String jsonPResult(String callback, boolean result, String message, Map<String, Object> data) {
        return toJsonP(callback, jsonResult(result, message, data));
    }

    public static String jsonPResult(String callback, int result, String message, Map<String, Object> data) {
        return toJsonP(callback, jsonResult(result, message, data));
    }

    private static String toJsonP(String callback, String result) {
        return StringUtil.isEmpty(callback)?result:callback + "(" + result + ")";
    }
}
