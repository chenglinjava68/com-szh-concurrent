package com.szh.util.common.pool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.DecompressingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtils {
    private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtils.class);
    private static volatile HttpClient httpClient;
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String EMPTY_STRING = "";
    private static final String SEP = "/";
    private static final String DEFAULT_PROXY_HOST = "10.10.65.165";
    private static final int DEFAULT_PROXY_PORT = 3128;
    private static final int CONN_TTL = 1;
    private static final int DEFAULT_TIMEOUT = 15;
    private static final TimeUnit DEFAULT_TIME_UNIT;

    private HttpClientUtils() {
    }

    private static HttpParams getClientConfig() {
        boolean timeout = true;
        SyncBasicHttpParams clientParams = new SyncBasicHttpParams();
        HttpClientParams.setConnectionManagerTimeout(clientParams, 20000L);
        HttpConnectionParams.setConnectionTimeout(clientParams, 20000);
        HttpConnectionParams.setSoTimeout(clientParams, 20000);
        HttpConnectionParams.setSoKeepalive(clientParams, true);
        HttpProtocolParams.setContentCharset(clientParams, "UTF-8");
        ConnRouteParams.setDefaultProxy(clientParams, new HttpHost("10.10.65.165", 3128));
        clientParams.setParameter("http.protocol.cookie-policy", "ignoreCookies");
        return clientParams;
    }

    public static HttpClient getHttpClient() {
        if(null == httpClient) {
            Class var0 = HttpClientUtils.class;
            synchronized(HttpClientUtils.class) {
                if(null == httpClient) {
                    PoolingClientConnectionManager manager = new PoolingClientConnectionManager(SchemeRegistryFactory.createDefault(), 1L, TimeUnit.SECONDS);
                    manager.setDefaultMaxPerRoute(10);
                    manager.setMaxTotal(100);
                    DefaultHttpClient backend = new DefaultHttpClient(manager, getClientConfig());
                    httpClient = new DecompressingHttpClient(backend);
                    httpClient = backend;
                }
            }
        }

        return httpClient;
    }

    public static HttpClient getThreadSafeClient() {
        return getHttpClient();
    }

    public static void execute(Collection<HttpUriRequest> requests) {
        int size = requests.size();
        final CountDownLatch latch = new CountDownLatch(size);
        Iterator var3 = requests.iterator();

        while(var3.hasNext()) {
            final HttpUriRequest request = (HttpUriRequest)var3.next();
            GlobalThreadPool.getGlobalThreadPool().submit(new Runnable() {
                public void run() {
                    try {
                        HttpClientUtils.doRequest(request);
                    } finally {
                        latch.countDown();
                    }

                }
            });
        }

        GlobalThreadPool.await(latch, (long)(15 * size), DEFAULT_TIME_UNIT);
    }

    private static HttpEntity doRequest(HttpUriRequest request) {
        HttpClientUtils.CachedHttpEntity entity = null;

        try {
            HttpResponse e = getThreadSafeClient().execute(request);
            entity = new HttpClientUtils.CachedHttpEntity(e.getEntity());
        } catch (Exception var6) {
            LOG.error("Execute http request {} error:", request, var6);
        } finally {
            if(request instanceof HttpRequestBase) {
                ((HttpRequestBase)request).releaseConnection();
            }

        }

        return entity;
    }

    public static Future<HttpEntity> execute(final HttpUriRequest request) {
        return GlobalThreadPool.getGlobalThreadPool().submit(new Callable() {
            public HttpEntity call() throws Exception {
                return HttpClientUtils.doRequest(request);
            }
        });
    }

    public static HttpEntity asyncExecute(HttpUriRequest request) {
        return asyncExecute(request, 15L);
    }

    public static HttpEntity asyncExecute(HttpUriRequest request, long timeout) {
        return asyncExecute(request, timeout, DEFAULT_TIME_UNIT);
    }

    public static HttpEntity asyncExecute(HttpUriRequest request, long timeout, TimeUnit unit) {
        HttpEntity entity = null;
        Future future = execute(request);

        try {
            entity = (HttpEntity)future.get(timeout, unit);
        } catch (Exception var8) {
            boolean result = future.cancel(true);
            LOG.error("Get request {} execute result {}:{} error, cancel it {}:", new Object[]{request, Long.valueOf(timeout), unit, Boolean.valueOf(result), var8});
        }

        return entity;
    }

    public static List<NameValuePair> append(List<NameValuePair> pairs, String key, String value) {
        pairs.add(new BasicNameValuePair(key, value));
        return pairs;
    }

    static {
        DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
    }

    static class CachedHttpEntity implements HttpEntity {
        private final HttpEntity httpEntity;
        private final byte[] buffer;

        CachedHttpEntity(HttpEntity httpEntity) throws IOException {
            this.httpEntity = httpEntity;
            this.buffer = this.createBuffer();
        }

        private byte[] createBuffer() throws IOException {
            InputStream inputStream = null;

            try {
                inputStream = this.httpEntity.getContent();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buff = new byte[100];
                boolean rc = false;

                int rc1;
                while((rc1 = inputStream.read(buff, 0, 100)) > 0) {
                    outputStream.write(buff, 0, rc1);
                }

                outputStream.close();
                byte[] var5 = outputStream.toByteArray();
                return var5;
            } finally {
                if(inputStream != null) {
                    inputStream.close();
                }

            }
        }

        public boolean isRepeatable() {
            return this.httpEntity.isRepeatable();
        }

        public boolean isChunked() {
            return this.httpEntity.isChunked();
        }

        public long getContentLength() {
            return this.httpEntity.getContentLength();
        }

        public Header getContentType() {
            return this.httpEntity.getContentType();
        }

        public Header getContentEncoding() {
            return this.httpEntity.getContentEncoding();
        }

        public InputStream getContent() throws IOException, IllegalStateException {
            return this.buffer != null && this.buffer.length > 0?new ByteArrayInputStream(this.buffer):null;
        }

        public void writeTo(OutputStream outstream) throws IOException {
            this.httpEntity.writeTo(outstream);
        }

        public boolean isStreaming() {
            return this.httpEntity.isStreaming();
        }

        public void consumeContent() throws IOException {
            this.httpEntity.consumeContent();
        }
    }
}
