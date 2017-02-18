package com.szh.util.common;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class UrlGeneratorUtil {
    private static final String EMPTY_STRING = "";

    public UrlGeneratorUtil() {
    }

    public static String generateUrl(String base, Map<String, String> params) {
        if(StringUtil.isEmpty(base)) {
            return "";
        } else if(params != null && !params.isEmpty()) {
            LinkedList p = new LinkedList();
            Iterator format = params.entrySet().iterator();

            while(true) {
                while(true) {
                    Entry e;
                    String key;
                    do {
                        do {
                            if(!format.hasNext()) {
                                if(p.isEmpty()) {
                                    return base;
                                }

                                String format1 = "%s?%s";
                                if(base.indexOf(63) >= 0) {
                                    if(base.endsWith("&")) {
                                        format1 = "%s%s";
                                    } else {
                                        format1 = "%s&%s";
                                    }
                                }

                                return String.format(format1, new Object[]{base, StringUtil.join(p, "&")});
                            }

                            e = (Entry)format.next();
                        } while(StringUtil.isEmpty((String)e.getKey()));

                        key = UrlEncoderUtil.encodeByUtf8((String)e.getKey());
                    } while(StringUtil.isEmpty(key));

                    if(!StringUtil.isEmpty((String)e.getValue())) {
                        String value = UrlEncoderUtil.encodeByUtf8((String)e.getValue());
                        if(!StringUtil.isEmpty(value)) {
                            p.add(String.format("%s=%s", new Object[]{key, value}));
                            continue;
                        }
                    }

                    p.add(key);
                }
            }
        } else {
            return base;
        }
    }
}