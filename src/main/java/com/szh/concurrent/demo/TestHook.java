package com.szh.concurrent.demo;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by zhihaosong on 17-3-2.
 * 该方法用来在jvm中增加一个关闭的钩子。当程序正常退出,
 * 系统调用 System.exit方法或虚拟机被关闭时才会执行添加的shutdownHook线程。
 * 其中shutdownHook是一个已初始化但并不有启动的线程，当jvm关闭的时候，
 * 会执行系统中已经设置的所有通过方法addShutdownHook添加的钩子，
 * 当系统执行完这些钩子后，jvm才会关闭。
 * 所以可通过这些钩子在jvm关闭的时候进行内存清理、资源回收等工作。
 * <p>
 * 设置钩子，虚拟机关闭后并不会关闭掉执行的线程，
 */
public class TestHook {
    private static final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(100);

    private final static AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                atomicBoolean.set(false);
                while (queue.size() > 0) {
                    System.out.println(MessageFormat.format("ShutdownHook size:{0}",
                            Thread.currentThread().getName(), queue.size()));
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    System.out.println("shutdown 3s after");
                    Thread.sleep(3000);
                    System.out.println("shutdown hook over");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; atomicBoolean.get(); i++) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        queue.put(MessageFormat.format("key:{0}", i+1));
                        System.out.println(MessageFormat.format("{0} put size:{1}",
                                Thread.currentThread().getName(), queue.size()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                        System.out.println(MessageFormat.format("{0} take take take data {1} left size:{2}",
                                Thread.currentThread().getName(), queue.take(), queue.size()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(20);
            System.out.println("System is over ");
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
