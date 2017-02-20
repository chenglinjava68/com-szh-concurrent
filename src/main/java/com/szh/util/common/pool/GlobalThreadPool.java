package com.szh.util.common.pool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalThreadPool {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalThreadPool.class);
    private static final boolean DEBUG;
    private static final int SWITCH_POINT = 8;
    private static volatile ThreadPoolExecutor pool;

    private GlobalThreadPool() {
    }

    private static int numberOfThreads(int num, int den, int switchPoint) {
        int numberOfCPUs = Runtime.getRuntime().availableProcessors();
        int numberOfThreads = numberOfCPUs <= switchPoint?numberOfCPUs:switchPoint + (numberOfCPUs - switchPoint) * num / den;
        LOG.info("Max number of threads is {}", Integer.valueOf(numberOfThreads));
        return numberOfThreads;
    }

    public static <T> T complete(Future<T> future, long timeout, TimeUnit unit) {
        try {
            return future.get(timeout, unit);
        } catch (Exception var5) {
            future.cancel(true);
            return null;
        }
    }

    public static boolean await(CountDownLatch latch, long timeout, TimeUnit unit) {
        try {
            latch.await(timeout, unit);
            return true;
        } catch (InterruptedException var5) {
            LOG.error("Count down latch interrupted:{}{}:", new Object[]{Long.valueOf(timeout), unit, var5});
            return false;
        }
    }

    public static ThreadPoolExecutor getGlobalThreadPool() {
        if(null == pool) {
            Class var0 = GlobalThreadPool.class;
            synchronized(GlobalThreadPool.class) {
                if(null == pool) {
                    pool = new ThreadPoolExecutor(1, numberOfThreads(5, 8, 8), 3L, TimeUnit.SECONDS, new SynchronousQueue(), new CallerRunsPolicy());
                }
            }
        }

        return pool;
    }

    static {
        DEBUG = LOG.isDebugEnabled();
    }
}
