package com.szh.concurrent.taskSchedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorTest implements Runnable {
    private String jobName = "";

    public ScheduledExecutorTest(String jobName) {
        super();
        this.jobName = jobName;
    }

    @Override
    public void run() {
        System.out.println("execute " + jobName);
    }

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

        long initialDelay1 = 1;
        long period1 = 1;
        // 从现在开始1秒钟之后，每隔1秒钟执行一次job1
        // 每次执行时间为上一次任务开始起向后推一个时间间隔
        //是基于固定时间间隔进行任务调度
        service.scheduleAtFixedRate(
                new ScheduledExecutorTest("job1"), initialDelay1,
                period1, TimeUnit.SECONDS);

        long initialDelay2 = 1;
        long delay2 = 1;
        // 从现在开始2秒钟之后，每隔2秒钟执行一次job2
        // 每次执行时间为上一次任务结束起向后推一个时间间隔
        //取决于每次任务执行的时间长短，是基于不固定时间间隔进行任务调度。
        service.scheduleWithFixedDelay(
                new ScheduledExecutorTest("job2"), initialDelay2,
                delay2, TimeUnit.SECONDS);
    }
}