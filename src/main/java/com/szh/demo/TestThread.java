package com.szh.demo;

/**
 * Created by zhihaosong on 17-3-13.
 */
public class TestThread extends Thread implements Runnable {
    @Override
    public void run() {
        super.run();
        System.out.println("sdsfdfsd");
    }

    public static void main(String[] args) {
        new TestThread().start();
        String str = "asdf";
        char[] aabb = {'a', 'b', 'c'};

    }
}
