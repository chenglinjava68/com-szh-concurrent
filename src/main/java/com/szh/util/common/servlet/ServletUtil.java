package com.szh.util.common.servlet;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.szh.util.common.AesCryptUtil;
import com.szh.util.common.HtmlFilterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ServletUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletUtil.class);
    public static final int DEFAULT_PORT = 80;
    public static final int MAX_IP_LENGTH = 15;

    public ServletUtil() {
    }

    public static void clearUserCookie(HttpServletRequest request, HttpServletResponse response) {
        deleteCookie(request, response, "DJ_SELF");
        deleteCookie(request, response, "uchome_auth");
        deleteCookie(request, response, "dj_auth");
        deleteCookie(request, response, "dj_auth_v3");
        deleteCookie(request, response, "uchome_loginuser");
        deleteCookie(request, response, "login_status");
    }

    public static void writeUserAuthCookie(HttpServletResponse response, int uid, String password, String rootDomain, int expiry) {
        String loginAuthV3 = AesCryptUtil.encrypt(uid + "|" + password, "8e-a65Zq@_6rdRci3*!4R~885");
        writeCookie(response, "dj_auth_v3", loginAuthV3, rootDomain, expiry);
        writeCookie(response, "uchome_loginuser", String.valueOf(uid), rootDomain, expiry);
    }

    public static String getUrl(HttpServletRequest request) {
        if(request == null) {
            return "";
        } else {
            String url = "http://" + request.getServerName() + (request.getServerPort() == 80?"":":" + request.getServerPort()) + request.getContextPath() + request.getRequestURI();
            if(request.getQueryString() != null && !request.getQueryString().isEmpty()) {
                url = url + "?" + HtmlFilterUtil.filterHeaderValue(request.getQueryString());
            }

            return url;
        }
    }

    public static String getIp(HttpServletRequest request) {
        if(request == null) {
            return "";
        } else {
            String ip = request.getHeader("X-Forwarded-For");
            if(ip == null || "".equals(ip)) {
                ip = request.getRemoteAddr();
            }

            return ip.split(",")[0].trim().length() > 15?"":ip.split(",")[0].trim();
        }
    }

    public static String getWebRoot(HttpServletRequest request) {
        String webRoot = "";
        if(request == null) {
            return webRoot;
        } else if(request.getSession() == null) {
            LOGGER.debug("====>>>>> There is no session");
            return webRoot;
        } else {
            ServletContext servletContext = request.getSession().getServletContext();
            if(servletContext != null) {
                webRoot = servletContext.getRealPath("/").replaceAll("\\\\", "/");
                LOGGER.debug("====>>>>> The root path of web context is " + webRoot);
            } else {
                LOGGER.debug("====>>>>> There is no servlet context");
            }

            return webRoot;
        }
    }

    public static Cookie getCookie(HttpServletRequest request, String key) {
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

    public static boolean writeCookie(HttpServletResponse response, String name, String value, String domain, int time) {
        try {
            Cookie e = new Cookie(name, value);
            e.setPath("/");
            if(!domain.equals("localhost")) {
                e.setDomain(domain);
            }

            if(time > 0) {
                e.setMaxAge(time);
            }

            response.addCookie(e);
            return true;
        } catch (Exception var6) {
            LOGGER.error("write cookie {}:{} error, e:{}", new Object[]{name, value, var6.getMessage()});
            return false;
        }
    }

    public static boolean writeCookie(HttpServletResponse response, String name, String value, int time) {
        return writeCookie(response, name, value, "dajie.com", time);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        deleteCookie(request, response, (String)null, name);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String domain, String name) {
        Cookie cookie = null;
        if(request.getCookies() != null) {
            Cookie[] var5 = request.getCookies();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Cookie c = var5[var7];
                if(c.getName().equals(name)) {
                    c.setValue((String)null);
                    c.setMaxAge(0);
                    if(domain == null) {
                        domain = "dajie.com";
                    }

                    if(!domain.equals("localhost")) {
                        c.setDomain(domain);
                    }

                    c.setPath("/");
                    cookie = c;
                }
            }
        }

        if(cookie != null) {
            response.addCookie(cookie);
        }

    }

    public static boolean isCrossDomain(HttpServletRequest request) {
        String host = request.getServerName().toLowerCase();
        String referer = request.getHeader("Referer");
        return referer == null || !referer.toLowerCase().startsWith("http://" + host);
    }
}
