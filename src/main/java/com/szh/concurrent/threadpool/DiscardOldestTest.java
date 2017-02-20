package com.szh.concurrent.threadpool;

import java.text.MessageFormat;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhihaosong on 17-2-20.
 * 抛弃最旧的任务
 * 发现第二次和第三次提交的任务被抛弃了！
 */
public class DiscardOldestTest {
    public static void main(String[] args) {
        final ThreadPoolExecutor executor = new ThreadPoolExecutor
                (1, 3, 2, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(5));

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        for (int i = 0; i < 10; i++) {
            System.out.println(MessageFormat.format("第{0}次提交任务,当前等待队列长度{1}", i, executor.getQueue()
                    .size()));
            executor.execute(new MyTask("pre-".concat(i + "")));

        }
        executor.shutdown();

    }

    public static class MyTask implements Runnable {

        private String name;

        @Override
        public void run() {
            System.out.println(name);
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                //logger.error("", e);
                e.printStackTrace();
            }
        }

        public MyTask(String name) {
            super();
            this.name = name;
        }

    }

}
