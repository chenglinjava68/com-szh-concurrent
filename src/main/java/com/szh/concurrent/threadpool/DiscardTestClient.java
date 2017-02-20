package com.szh.concurrent.threadpool;

import java.text.MessageFormat;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhihaosong on 17-2-20.
 * 抛弃当前的任务
 * 提交了10个任务最终只有8个任务被执行！其中有两次被抛弃了！
 */
public class DiscardTestClient {


    public static void main(String[] args) {
        final ThreadPoolExecutor executor = new ThreadPoolExecutor
                (1, 3, 2, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(5));

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        for (int i = 0; i < 10; i++) {
            System.out.println(MessageFormat.format("第{0}次提交任务,当前等待队列长度{1}", i + 1, executor.getQueue()
                    .size()));
            executor.execute(new Runnable() {

                public void run() {
                    System.out.println(Thread.currentThread().getName()+"");
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        //logger.error("", e);  
                    }
                }
            });

        }
        executor.shutdown();

    }

}  