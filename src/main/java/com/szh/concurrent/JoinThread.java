package com.szh.concurrent;

/**
 * Created by zhihaosong on 16-11-23.
 */
public class JoinThread extends Thread {
    private static int n = 0;

    public static synchronized void inc() {
        n++;
    }

    private JoinThread() {
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                inc();
                sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Throwable {

        Thread thread[] = new Thread[100];
        for (int i = 0; i < thread.length; i++) {
            thread[i] = new JoinThread();
        }
        for (int i = 0; i < thread.length; i++) {
            thread[i].start();
        }
        System.out.println(args.length);
        if (args.length > 0) {
            System.out.println(args.length);

            for (int i = 0; i < thread.length; i++) {
                thread[i].join();
            }
        }
        System.out.println(JoinThread.n);
    }
}
