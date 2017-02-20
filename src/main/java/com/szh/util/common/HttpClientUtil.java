package com.szh.util.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.Cookie;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
    private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
    public static final String GET = "get";
    public static final String POST = "post";

    public HttpClientUtil() {
    }

    public static String getResponse(String url) {
        return response(url, (Map)null, (Cookie[])null, "get");
    }

    public static String getResponse(String url, int timeoutMilliseconds) {
        return response(url, (Map)null, (Cookie[])null, "get", timeoutMilliseconds);
    }

    public static String postResponse(String url, Map<String, String> parameters, Cookie[] cookies) {
        return response(url, parameters, cookies, "post");
    }

    public static String postResponse(String url, Map<String, String> parameters, Cookie[] cookies, int timeoutMilliseconds) {
        return response(url, parameters, cookies, "post", timeoutMilliseconds);
    }

    public static String response(String url, Map<String, String> parameters, Cookie[] cookies, String type) {
        return response(url, parameters, cookies, type, 8000);
    }

    public static String response(String url, Map<String, String> parameters, Cookie[] cookies, String type, int timeoutMilliseconds) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter("http.connection.timeout", Integer.valueOf(timeoutMilliseconds));
        httpclient.getParams().setParameter("http.socket.timeout", Integer.valueOf(timeoutMilliseconds));
        BasicHttpContext localContext = null;
        if(cookies != null) {
            BasicCookieStore method = new BasicCookieStore();
            localContext = new BasicHttpContext();
            localContext.setAttribute("http.cookie-store", method);
            Cookie[] sb = cookies;
            int e = cookies.length;

            for(int entity = 0; entity < e; ++entity) {
                Cookie contentTypeHeader = sb[entity];
                BasicClientCookie charset = new BasicClientCookie(contentTypeHeader.getName(), contentTypeHeader.getValue());
                charset.setVersion(1);
                charset.setDomain(contentTypeHeader.getDomain() == null?url.substring(7, url.indexOf(47, 7)):contentTypeHeader.getDomain());
                charset.setPath(contentTypeHeader.getPath() == null?"/":contentTypeHeader.getPath());
                charset.setExpiryDate(new Date(contentTypeHeader.getMaxAge() < 0?System.currentTimeMillis() + 3600000L:System.currentTimeMillis() + (long)(contentTypeHeader.getMaxAge() * 1000)));
                method.addCookie(charset);
            }
        }

        Object var19;
        StringBuilder var20;
        Iterator var22;
        String var24;
        if(type.equals("get")) {
            var19 = new HttpGet(url);
            if(parameters != null) {
                var20 = new StringBuilder();
                var22 = parameters.keySet().iterator();

                while(var22.hasNext()) {
                    var24 = (String)var22.next();
                    var20.append("&").append(var24).append("=").append((String)parameters.get(var24));
                }

                if(url.indexOf(63) > 0) {
                    url = url + var20.toString();
                } else {
                    url = url + "?";
                    url = url + var20.deleteCharAt(0).toString();
                }
            }
        } else {
            var19 = new HttpPost(url);
            if(parameters != null) {
                ArrayList var21 = new ArrayList();
                var22 = parameters.keySet().iterator();

                while(var22.hasNext()) {
                    var24 = (String)var22.next();
                    var21.add(new BasicNameValuePair(var24, (String)parameters.get(var24)));
                }

                try {
                    ((HttpPost)var19).setEntity(new UrlEncodedFormEntity(var21, "UTF-8"));
                } catch (UnsupportedEncodingException var17) {
                    log.warn("response:" + url + " error " + var17.getMessage());
                }
            }
        }

        ((HttpUriRequest)var19).setHeader("Connection", "close");
        var20 = new StringBuilder();

        try {
            HttpResponse var23;
            if(localContext != null) {
                var23 = httpclient.execute((HttpUriRequest)var19, localContext);
            } else {
                var23 = httpclient.execute((HttpUriRequest)var19);
            }

            HttpEntity var25 = var23.getEntity();
            if(var25 == null) {
                return "";
            }

            Header var26 = var25.getContentType();
            String var27 = "UTF-8";
            if(var26 != null) {
                String in = var25.getContentType().getValue();
                var27 = StringUtil.isEmpty(in)?"UTF-8":in.replaceAll("([^;]*);\\s*charset=", "");
            }

            try {
                if(!Charset.isSupported(var27)) {
                    var27 = "UTF-8";
                    log.warn("charset not supported:" + var27 + ";url=" + url);
                }
            } catch (Exception var16) {
                var27 = "UTF-8";
            }

            InputStream var28 = var25.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(var28, var27));

            String s;
            while((s = reader.readLine()) != null) {
                var20.append(s).append("\r\n");
            }
        } catch (Exception var18) {
            log.warn("===>>>response error. url:{},error:{}", url, var18);
        }

        return var20.toString();
    }
}
