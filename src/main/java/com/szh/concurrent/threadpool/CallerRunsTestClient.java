package com.szh.concurrent.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhihaosong on 17-2-20.
 * 返回给调用者的饱和策略
 *  终止饱和策略是:当提交的任务无法进入等待队列且线程池中创建的线程数量已经达到了最大线程数量的限制，
 *  则会拒绝新提交的任务。
 */
public class CallerRunsTestClient {
    public static void main(String[] args) {
        //等待队列
        final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(5);

        ThreadPoolExecutor executor = new ThreadPoolExecutor
                (3, 5, 2, TimeUnit.SECONDS, queue);

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 15; i++) {
            executor.execute(new Runnable() {

                public void run() {
                    System.out.println("run-" + Thread.currentThread().getName() + " queue:"
                            + queue.size());
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        //logger.error("", e);
                        e.printStackTrace();
                    }
                }
            });
        }
        System.out.println("run-" + Thread.currentThread().getName() + " queue:"
                + queue.size()+"-over");
    }
}
