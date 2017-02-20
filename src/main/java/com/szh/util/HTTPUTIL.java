package com.szh.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CodingErrorAction;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jianguo.li
 * @date 2014年2月21日
 * @descriable 发送HTTP请求的工具类，主要是对apache httpclient4.3的进行下一步的封装
 */
public final class HTTPUTIL {

    private static final Logger LOG = Logger.getLogger(HTTPUTIL.class);

    private static final int HTTP_CONNECT_TIME_OUT_IN_MILLIS = 5000;
    private static final int HTTP_SOCKET_TIME_OUT_IN_MILLIS = 5000;
    private static final int HTTP_REQUEST_CONNECT_TIME_OUT_IN_MILLIS = 3000;
    private static final int HTTP_MAX_CONNECTIONS = 1000;
    private static final int MAX_RETRY_TIMES = 3;

    private static final int MAX_HTTP_HEADER_COUNT = 200;
    private static final int MAX_HTTP_BODY_LINE_COUNT = 100000;

    private static CloseableHttpClient httpClient;

    private HTTPUTIL() {

    }

    static {
        try {

            // 设置HTTPS链接的信息
            SSLContext sslContext = SSLContexts.custom().useTLS().build();

            sslContext.init(null,

                    new TrustManager[]{new X509TrustManager() {

                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs,
                                                       String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs,
                                                       String authType) {
                        }

                    }}, null);

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https",
                            new SSLConnectionSocketFactory(sslContext)).build();

            // 构建HTTP链接池
            PoolingHttpClientConnectionManager ClientConnectionManager = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);

            RequestConfig requestConfig = RequestConfig
                    .custom()
                            // 设置建立TCP连接的最大等待时间（毫秒）
                    .setConnectTimeout(HTTP_CONNECT_TIME_OUT_IN_MILLIS)
                            // 设置从HTTP连接池中获取一个链接的最大等待时间（毫秒）
                    .setConnectionRequestTimeout(
                            HTTP_REQUEST_CONNECT_TIME_OUT_IN_MILLIS)
                            // 设置获取获取响应的最大等待时间（毫秒）
                    .setSocketTimeout(HTTP_SOCKET_TIME_OUT_IN_MILLIS)
                            // 设置成浏览器兼容的模式
                    .setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();

            SocketConfig socketConfig = SocketConfig.custom()
                    // 因为是LAN所以讲tcpNoDelay打开
                    .setTcpNoDelay(true).build();

            ClientConnectionManager.setDefaultSocketConfig(socketConfig);
            // Create message constraints
            MessageConstraints messageConstraints = MessageConstraints.custom()
                    // 设置能接受HTTP头的最大数量
                    .setMaxHeaderCount(MAX_HTTP_HEADER_COUNT)
                            // 设置能接受的HTTP体最大的数量
                    .setMaxLineLength(MAX_HTTP_BODY_LINE_COUNT).build();

            // Create connection configuration
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE)
                            // 设置HTTP的编码
                    .setCharset(Consts.UTF_8)
                    .setMessageConstraints(messageConstraints).build();

            ClientConnectionManager
                    .setDefaultConnectionConfig(connectionConfig);
            // 连接池里的最大连接数
            ClientConnectionManager.setMaxTotal(HTTP_MAX_CONNECTIONS);
            // 每个路由的默认最大连接数,这服务器的数量以及连接池的最大连接数有关
            ClientConnectionManager
                    .setDefaultMaxPerRoute(HTTP_MAX_CONNECTIONS / 2);

            // 设置默认的HTTP请求头信息
            List<BasicHeader> defaultHeaders = new ArrayList<BasicHeader>() {

                private static final long serialVersionUID = 1263811764541797122L;

                {
                    // 不使用缓存，默认的HTTP Client就是禁用缓存的，这里就画蛇添足一下，以防止万一:P
                    add(new BasicHeader("Expires", "0"));
                    add(new BasicHeader("Cache-Control", "no-store"));
                }
            };

            httpClient = HttpClientBuilder
                    .create()
                            // 设置HTTP的重试策略，目前设置为重新尝试3次（已关闭重试）
                    .setRetryHandler(
                            new DefaultHttpRequestRetryHandler(MAX_RETRY_TIMES,
                                    false))
                    .setConnectionManager(ClientConnectionManager)
                    .setDefaultRequestConfig(requestConfig)
                    .setDefaultHeaders(defaultHeaders).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

   /*
    *
    发送HTTP GET
    请求并并将返回值以字符串的形式返回，默认以UTF-8的编码格式返回
    *
            *
    @param
    url
    *请求地址
    *
    @param
    queryParameterPair
    *查询参数
    *
    @param
    encoding
    *期望的返回的字符编码格式，默认的编码设置为UTF-8
            *
            *@return
            */

    public static String sendGetRequest(String url,
                                        Map<String, String> queryParameterPair, String encoding) {

        String result = null;
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;

        try {

            URIBuilder UriBuilder = new URIBuilder(url);

            if (MapUtils.isNotEmpty(queryParameterPair)) {
                for (Map.Entry<String, String> param : queryParameterPair
                        .entrySet()) {
                    UriBuilder.addParameter(param.getKey(), param.getValue());
                }
            }

            if (LOG.isInfoEnabled()) {
                LOG.info("request url[" + UriBuilder.build() + "]");
            }

            httpGet = new HttpGet(UriBuilder.build());

            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                LOG.error("send get request url[" + UriBuilder.build()
                        + "] return http status code = " + statusCode);
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try {
                    if (StringUtils.isBlank(encoding)) {
                        encoding = "UTF-8";
                    }
                    result = EntityUtils.toString(entity, encoding);
                } finally {
                    // 这个方法也可以把底层的流给关闭了
                    EntityUtils.consume(entity);
                }
            }

        } catch (Exception e) {
            LOG.error("occur error : ", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    LOG.error("close response occur error :", e);
                }
            }
        }
        return result;
    }

    /* @param
     url
     *请求的URI包含QueryString的信息
     *
     @param
     encoding
     *期望的返回的字符编码格式，默认的编码设置为UTF-8
      *@return
    */
    public static String sendGetRequest(String url, String encoding) {

        String result = null;
        HttpGet httpGet = null;

        try {
            httpGet = new HttpGet(url);

            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK) {
                LOG.error("send get request url[" + url
                        + "] return http status code = " + statusCode);
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try {
                    if (StringUtils.isBlank(encoding)) {
                        encoding = "UTF-8";
                    }
                    result = EntityUtils.toString(entity, encoding);
                } finally {
                    // 这个方法也可以把底层的流给关闭了
                    EntityUtils.consume(entity);
                }
            }

        } catch (Exception e) {
            LOG.error("occur error : ", e);
        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
        }
        return result;
    }

  /*  /*/

    /**
     * 发送HTTP GET请求
     * ，并将返回结果以字节数组的形式返回
     *
     * @param url                请求的地址
     * @param queryParameterPair 请求的参数列表
     * @return
     *//*/*/
    public static byte[] sendGetRequest(String url,
                                        Map<String, String> queryParameterPair) {

        byte[] result = null;
        HttpGet httpGet = null;

        try {

            URIBuilder UriBuilder = new URIBuilder(url);

            if (MapUtils.isNotEmpty(queryParameterPair)) {
                for (Map.Entry<String, String> param : queryParameterPair
                        .entrySet()) {
                    UriBuilder.addParameter(param.getKey(), param.getValue());
                }
            }
            httpGet = new HttpGet(UriBuilder.build());
            HttpResponse response = httpClient.execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    try {
                        result = EntityUtils.toByteArray(entity);
                    } finally {
                        // 这个方法也可以把底层的流给关闭了
                        EntityUtils.consume(entity);
                    }
                }
            } else {
                LOG.error("send get request url[" + UriBuilder.build()
                        + "] return http status code = " + statusCode);
            }
        } catch (Exception e) {
            LOG.error("occur error : ", e);
        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
        }
        return result;
    }

    /* 发送HTTP GET请求
     * ，并将返回结果以字节数组的形式返回
     *
             *
     @param
     url 请求的地址
     *
     @param
     queryParameterPair 请求的参数列表
     *@return
 */
    public static byte[] sendGetRequest(String url) {

        byte[] result = null;

        HttpGet httpGet = null;

        try {
            httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    try {
                        result = EntityUtils.toByteArray(entity);
                    } finally {
                        // 这个方法也可以把底层的流给关闭了
                        EntityUtils.consume(entity);
                    }
                }
            } else {
                LOG.error("send get request url[" + url
                        + "] return http status code = " + statusCode);
            }
        } catch (Exception e) {
            LOG.error("occur error : ", e);
        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
        }
        return result;
    }

  /*  发送 HTTP
    POST的请求，并将返回结果以字符串的形式返回
    *
            *
    @param
    url
    *请求地址
    *
    @param
    content
    *发送的内容
    *
    @param
    encoding
    *设置字符编码
    *
    @param
    isSetHeader
    *是否设置Content-
    type http头字段
    *@return*/

    public static String sendPostRequest(String url, String content,
                                         String encoding, boolean isSetHeader) {

        HttpPost httpPost = new HttpPost(url);
        if (isSetHeader) {
            httpPost.addHeader("Content-Type",
                    "application/x-www-form-urlencoded");
        }
        String result = null;
        try {
            StringEntity strEntity = new StringEntity(content, encoding);
            strEntity.setContentEncoding(encoding);
            httpPost.setEntity(strEntity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {
                try {
                    if (StringUtils.isBlank(encoding)) {
                        encoding = "UTF-8";
                    }
                    result = EntityUtils.toString(resEntity, encoding).trim();
                } finally {
                    EntityUtils.consume(resEntity);
                }
            }
        } catch (Exception e) {
            LOG.error("occur error :", e);
        } finally {
            httpPost.abort();
        }

        return result;
    }

  /*  *上传文件
    *
            *
    @param
    url
    *请求的地址
    *
    @param
    fileField
    *文件的上传的字段名称
    *
    @param
    file
    *将要上传的文件
    *
    @param
    params
    *将要设置的参数列表
    *@return*/

    public static String uploadFile(String url, String fileField, File file,
                                    Map<String, String> params) {

        String result = null;
        HttpPost httpPost = null;

        try {

            httpPost = new HttpPost(url);
            FileBody fileBody = new FileBody(file);
            MultipartEntityBuilder reqEntityBuilder = MultipartEntityBuilder
                    .create();
            reqEntityBuilder.addPart(fileField, fileBody);
            for (Map.Entry<String, String> mapEntry : params.entrySet()) {
                reqEntityBuilder.addPart(mapEntry.getKey(), new StringBody(
                        mapEntry.getValue(), ContentType.TEXT_PLAIN));
            }
            // 设置请求
            httpPost.setEntity(reqEntityBuilder.build());
            // 执行
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try {
                    result = EntityUtils.toString(entity).trim();
                } finally {
                    EntityUtils.consume(entity);
                }
            }
        } catch (Exception e) {
            LOG.error(" occur error ", e);
        } finally {
            httpPost.abort();
        }
        return result;
    }

  /*  发送HTTP POST请求以JSON的格式发送
    *
            *
    @param
    url
    *
    @param
    jsonHttpBody
    *
    @param
    queryStringPairs
    *
    @param
    encoding
    *@return*/

    public static String sendPostRequestWithJsonEntity(String url,
                                                       JsonObject jsonHttpBody) {

        DefaultHttpClient client = new DefaultHttpClient();
        try {
            URIBuilder builder = new URIBuilder(url);
            HttpPost postRequest = new HttpPost(
                    builder.build());

            String jsonString = new Gson().toJson(jsonHttpBody);

            StringEntity input = new StringEntity(jsonString);

            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = client.execute(postRequest);

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (response.getEntity().getContent())));

            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            return sb.toString();
        } catch (Exception e) {
            LOG.error("[POST DATA]", e);
            return null;
        } finally {
            client.getConnectionManager().shutdown();
        }
    }

   /* public static Pair<String, Map<String, String>> sendPostRequestWithHeader(String host, String queryString, String httpBody, Map<String, String> headers, String encoding, String... headNames) {

        Pair<String, Map<String, String>> result = new Pair<String, Map<String, String>>("", new HashMap<String, String>());

        if (StringUtils.isNotBlank(queryString)) {
            host = host + "?" + queryString;
        }

        LOG.info("[request url=" + host + "]");

        HttpPost httpPost = new HttpPost(host);

        try {

            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpPost.addHeader(header.getKey(), header.getValue());
            }

            // 设置请求
            if (StringUtils.isNotBlank(httpBody)) {
                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                StringEntity stringEntity = new StringEntity(httpBody, encoding);
                stringEntity.setContentEncoding(encoding);
                httpPost.setEntity(stringEntity);
            }
            LOG.info("[request http body=" + httpBody + "]");

            HttpResponse response = httpClient.execute(httpPost);

            if (null != headNames) {
                Map<String, String> heads = new HashMap<String, String>();
                for (String headName : headNames) {
                    if (StringUtils.isNotBlank(headName) && null != response.getFirstHeader(headName)) {
                        heads.put(headName, response.getFirstHeader(headName).getValue());
                    }
                }
                result.setValue(heads);
            }

            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                LOG.error("[send post request return http status code :" + statusCode);
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try {
                    result.setKey(EntityUtils.toString(entity, encoding).trim());
                } finally {
                    EntityUtils.consume(entity);
                }
            }

        } catch (Exception e) {
            LOG.error(" catch error : " + e);
        } finally {
            httpPost.abort();
        }
        return result;
    }*/

   /* public static String nativeSendGetRequest(String url, boolean useProxy) throws Exception {

        HttpURLConnection connection = null;
        BufferedReader in = null;

        String result = null;

        try {
            URL urlAddress = new URL(url);

            if (useProxy) {
                final String proxyHost = GameConfigLogic.INSTANCE.getGameConfigValue("proxyUrl");
                final int proxyPort = Integer.valueOf(GameConfigLogic.INSTANCE.getGameConfigValue("proxyPort"));

                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                connection = (HttpURLConnection) urlAddress.openConnection(proxy);
            } else {
                connection = (HttpURLConnection) urlAddress.openConnection();
            }

            connection.setRequestMethod("get");
            connection.setConnectTimeout(HTTP_CONNECT_TIME_OUT_IN_MILLIS);
            connection.setReadTimeout(HTTP_SOCKET_TIME_OUT_IN_MILLIS);
            connection.setDefaultUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            final int stateCode = connection.getResponseCode();

            if (200 != stateCode) {
                LOG.error("request url[" + url + "] status code[" + stateCode + "]");
            }

            in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer response = new StringBuffer();

            String inputLine = null;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            result = response.toString();

        } catch (Exception e) {
            LOG.error("occur error:", e);
        } finally {
            if (null != in) {
                in.close();
            }
            if (null != connection) {
                connection.connect();
            }
        }

        return result;
    }*/
}
