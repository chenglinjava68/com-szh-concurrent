package com.szh.util.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeedUtil {
    private static Logger log = LoggerFactory.getLogger(FeedUtil.class);

    private FeedUtil() {
    }

    public static boolean addFeed(HashMap<String, String> feedData, String postUrl) {
        String url = "http://app.dajie.com/api/feed/create_feed.jsp";
        boolean result = false;

        String html;
        try {
            html = HttpClientUtil.postResponse(url, feedData, (Cookie[])null);
            if(html.indexOf("\"errorid\":\"0\"") > -1) {
                result = true;
            } else {
                log.error("add feed error:" + html);
            }
        } catch (Exception var10) {
            if(var10.getMessage().indexOf("timed out") > -1) {
                log.error("add feed error,try....");

                try {
                    html = HttpClientUtil.postResponse(url, feedData, (Cookie[])null);
                    if(html.indexOf("\"errorid\":\"0\"") > -1) {
                        result = true;
                    } else {
                        log.error("add feed error2:" + html);
                    }
                } catch (Exception var9) {
                    log.error("addFeed error2:", var10);
                    result = false;
                }
            } else {
                String value = "";

                Entry entry;
                for(Iterator var7 = feedData.entrySet().iterator(); var7.hasNext(); value = value + (String)entry.getKey() + "=" + (String)entry.getValue() + "\n") {
                    entry = (Entry)var7.next();
                }

                log.error("addFeed error1:" + value, var10);
                result = false;
            }
        }

        return result;
    }
}
