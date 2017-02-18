package com.szh.util.common.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolUtils {
    private static final Logger log = LoggerFactory.getLogger(ThreadPoolUtils.class);
    private static volatile ThreadPoolExecutor executor;
    private static Thread daemon = new Thread(new Runnable() {
        public void run() {
            while(true) {
                ThreadPoolUtils.log.info(String.format("Thread pool stats: total task: %d, max threads: %d, current pool size: %d, largest pool size: %d, running: %d.", new Object[]{Long.valueOf(ThreadPoolUtils.getThreadPool().getCompletedTaskCount()), Integer.valueOf(ThreadPoolUtils.getThreadPool().getMaximumPoolSize()), Integer.valueOf(ThreadPoolUtils.getThreadPool().getPoolSize()), Integer.valueOf(ThreadPoolUtils.getThreadPool().getLargestPoolSize()), Integer.valueOf(ThreadPoolUtils.getThreadPool().getActiveCount())}));

                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(10L));
                } catch (InterruptedException var2) {
                    ThreadPoolUtils.log.warn("daemon thread ", var2);
                }
            }
        }
    });

    private static void startMonitor() {
        daemon.setDaemon(true);
        daemon.start();
    }

    private ThreadPoolUtils() {
    }

    public static void wait(CountDownLatch latch) {
        if(null != latch) {
            try {
                latch.await();
            } catch (InterruptedException var2) {
                log.error(String.format("InterruptedException CountDownLatch: %d", new Object[]{Long.valueOf(latch.getCount())}), var2);
            }

        }
    }

    public static <T> void onComplete(final Future<T> future, final Callback<T> callback) {
        if(future != null && callback != null) {
            getThreadPool().execute(new Runnable() {
                public void run() {
                    callback.perform(ThreadPoolUtils.get(future));
                }
            });
        }
    }

    public static <T> T get(Future<T> future) {
        if(future == null) {
            return null;
        } else {
            try {
                return future.get();
            } catch (InterruptedException var2) {
                log.error("Future.get()", var2);
            } catch (ExecutionException var3) {
                log.error("Future.get()", var3);
            }

            return null;
        }
    }

    public static ThreadPoolExecutor getThreadPool() {
        if(null == executor) {
            Class var0 = ThreadPoolUtils.class;
            synchronized(ThreadPoolUtils.class) {
                if(null == executor) {
                    executor = (new ThreadPoolBuilder()).corePoolSize(1).maximumPoolSize(70).build();
                    startMonitor();
                }
            }
        }

        return executor;
    }
}
