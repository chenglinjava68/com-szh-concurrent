package com.szh.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhihaosong on 16-3-10.
 */
public class ThreadPool {
    private static final Logger log = LoggerFactory.getLogger(ThreadPool.class);
    private static ThreadPool pool = null;
    private ThreadPoolExecutor executor = null;
    private static final Object poolLock = new Object();
    private static final Object executorLock = new Object();

    private ThreadPool() {
    }

    ;

    public static ThreadPool instance() {

        if (pool == null) {
            synchronized (poolLock) {
                if (pool == null) {
                    pool = new ThreadPool();
                }
            }
        }
        return pool;
    }

    public void init(int coreSize, int maxSize) {
        if (executor == null) {
            synchronized (executorLock) {
                if (executor == null) {
                    executor = new ThreadPoolExecutor(coreSize, maxSize, 5,
                            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
                            25), new ThreadPoolExecutor.CallerRunsPolicy());
                }
            }
        }
    }

    public boolean excute(Runnable r) {

        if (executor == null) {
            init(15, 30);
        }

        try {
            executor.execute(r);
            return true;
        } catch (RejectedExecutionException e) {
            log.error("excute --> error:{}", e.getMessage());
            return false;
        }

    }
}
