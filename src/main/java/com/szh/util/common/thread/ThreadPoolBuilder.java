package com.szh.util.common.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ThreadPoolBuilder {
    private static final Log log = LogFactory.getLog(ThreadPoolBuilder.class);
    private int corePoolSize = 10;
    private int maximumPoolSize = 30;
    private long keepAliveTime = 3L;
    private TimeUnit unit;
    private BlockingQueue<Runnable> workQueue;
    private RejectedExecutionHandler handler;

    public ThreadPoolBuilder() {
        this.unit = TimeUnit.MINUTES;
        this.workQueue = new SynchronousQueue();
        this.handler = new CallerRunsPolicy();
    }

    public ThreadPoolBuilder corePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    public ThreadPoolBuilder maximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
        return this;
    }

    public ThreadPoolBuilder keepAliveTime(long keepAliveTime) {
        return this.keepAliveTime(keepAliveTime, TimeUnit.MINUTES);
    }

    public ThreadPoolBuilder keepAliveTime(long keepAliveTime, TimeUnit unit) {
        this.keepAliveTime = keepAliveTime;
        this.unit = unit;
        return this;
    }

    public ThreadPoolBuilder rejectedExecutionHandler(RejectedExecutionHandler handler) {
        this.handler = handler;
        return this;
    }

    public ThreadPoolBuilder workQueue(BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        return this;
    }

    public ThreadPoolExecutor build() {
        return new ThreadPoolExecutor(this.corePoolSize, this.maximumPoolSize, this.keepAliveTime, this.unit, this.workQueue, this.handler);
    }
}