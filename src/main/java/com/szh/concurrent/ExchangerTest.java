package com.szh.concurrent;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final Exchanger exchanger = new Exchanger();
        service.execute(new Runnable() {
            public void run() {
                try {

                    String data1 = "zxx";
                    System.out.println(Thread.currentThread().getName() +
                            "" + data1);
                    Thread.sleep((long) (Math.random() * 10000));
                    String data2 = (String) exchanger.exchange(data1);
                    System.out.println(Thread.currentThread().getName() + data2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        service.execute(new Runnable() {
            public void run() {
                try {

                    String data1 = "lhm";
                    System.out.println("" + Thread.currentThread().getName() + data1);
                    Thread.sleep((long) (Math.random() * 10000));
                    String data2 = (String) exchanger.exchange(data1);
                    System.out.println(Thread.currentThread().getName() + data2);
                } catch (Exception e) {

                }
            }
        });
    }
}
