package com.szh.util.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoginAuthParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuthParser.class);

    private LoginAuthParser() {
    }

    public static int getLoginUid(HttpServletRequest request) {
        int uid = getUidFromV3LoginCookie(request);
        if(uid <= 0 && !hasV3LoginCookie(request)) {
            uid = getUidFromV2LoginCookie(request);
        }

        return uid;
    }

    private static int getUidFromV2LoginCookie(HttpServletRequest request) {
        Cookie cookie = getCookie(request, "dj_auth");
        if(cookie != null && !StringUtil.isEmpty(cookie.getValue())) {
            String decodeValue = AesCryptUtil.decrypt(cookie.getValue(), "8e-Z6q@_Rdr6*ci3!4R~885");
            if(!StringUtil.isEmpty(decodeValue) && StringUtil.isInteger(decodeValue)) {
                int uid = Integer.parseInt(decodeValue);
                LOGGER.debug("====>>>>Get login user {} from v2 cookie {}", Integer.valueOf(uid), cookie.getValue());
                return uid;
            } else {
                LOGGER.warn(String.format("======>>>>Get uid from V2 cookie failed.cookie value is %s", new Object[]{cookie.getValue()}));
                return 0;
            }
        } else {
            return 0;
        }
    }

    private static int getUidFromV3LoginCookie(HttpServletRequest request) {
        Cookie c = getCookie(request, "dj_auth_v3");
        String decode;
        if(c != null && !StringUtil.isEmpty(c.getValue()) && !StringUtil.isEmpty(decode = AesCryptUtil.decrypt(c.getValue(), "8e-a65Zq@_6rdRci3*!4R~885"))) {
            String[] info = decode.split("\\|");
            if(info.length == 2 && StringUtil.isInteger(info[0])) {
                int uid = Integer.parseInt(info[0]);
                LOGGER.warn(String.format("======>>>>Get uid from V3 login cookie failed.cookie value is %s", new Object[]{c.getValue()}));
                return uid;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private static boolean hasV3LoginCookie(HttpServletRequest request) {
        Cookie c = getCookie(request, "dj_auth_v3");
        return c != null && !StringUtil.isEmpty(c.getValue());
    }

    private static Cookie getCookie(HttpServletRequest request, String key) {
        if(request.getCookies() == null) {
            return null;
        } else {
            Cookie[] var2 = request.getCookies();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Cookie c = var2[var4];
                if(c.getName().equals(key)) {
                    return c;
                }
            }

            return null;
        }
    }
}
