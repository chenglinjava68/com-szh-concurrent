package com.szh.concurrent.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhihaosong on 17-2-20.
 * 终止饱和策略
 * DiscardPolicy 是抛弃当前任务
 */
public class AbortTestClient {
    public static void main(String[] args) {
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 3, 2, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(5));

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        executor.setThreadFactory(new MyThreadFactory("tryCatchFianllyTest"));

        for (int i = 0; i < 10; i++) {
            try {
                executor.execute(new Runnable() {

                    public void run() {
                        try {
                            TimeUnit.MICROSECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("run-" + Thread.currentThread().getName());
                    }
                });
            } catch (RejectedExecutionException e) {
                System.out.println("第" + i + "次提交线程被拒绝!  当前活动线程数：" + executor.getActiveCount()
                        + " 队列长度：" + executor.getQueue().size());
            }
        }
    }
}

class MyThreadFactory implements ThreadFactory {
    private String PREFIX = "";
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public MyThreadFactory(String threadName) {
        super();
        this.PREFIX = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, PREFIX + atomicInteger.incrementAndGet());
    }
}
