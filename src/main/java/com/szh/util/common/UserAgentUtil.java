package com.szh.util.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAgentUtil {
    private static final Logger logger = LoggerFactory.getLogger(UserAgentUtil.class);
    private static final Pattern IE_P = Pattern.compile("MSIE (\\d)");
    private static final Pattern FF_P = Pattern.compile("Firefox/(\\d)");

    public UserAgentUtil() {
    }

    public static String[] getUA(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        return getUA(ua);
    }

    public static String[] getUA(String ua) {
        if(StringUtil.isEmpty(ua)) {
            return new String[]{null, null};
        } else {
            String os = null;
            String browser = null;
            if(ua.contains("Win")) {
                os = "ua-win";
            } else if(ua.contains("Mac")) {
                os = "ua-mac";
            }

            Matcher iem = IE_P.matcher(ua);
            if(iem.find()) {
                browser = "ua-ie" + iem.group(1);
            } else {
                Matcher uaLog = FF_P.matcher(ua);
                if(uaLog.find()) {
                    browser = "ua-ff" + uaLog.group(1);
                } else if(ua.contains("AppleWebKit")) {
                    browser = "ua-wk";
                }
            }

            StringBuffer uaLog1 = new StringBuffer("User Agent info : ");
            uaLog1.append(ua).append(". And Result:").append(os).append(";").append(browser);
            logger.info(uaLog1.toString());
            return new String[]{os, browser};
        }
    }
}
