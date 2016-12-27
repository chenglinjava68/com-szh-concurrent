package com.szh.concurrent;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final CyclicBarrier cb = new CyclicBarrier(3);
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println(Thread.currentThread().getName() +
                                +(cb.getNumberWaiting() + 1) + (cb.getNumberWaiting() == 2));
                        cb.await();

                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println(Thread.currentThread().getName() +
                                (cb.getNumberWaiting() + 1) + (cb.getNumberWaiting() == 2));
                        cb.await();
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println(Thread.currentThread().getName() +
                                (cb.getNumberWaiting() + 1) + (cb.getNumberWaiting() == 2));
                        cb.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }
        service.shutdown();
    }
}
