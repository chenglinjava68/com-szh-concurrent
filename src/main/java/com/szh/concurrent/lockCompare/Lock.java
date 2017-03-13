package com.szh.concurrent.lockCompare;

import java.util.concurrent.locks.ReentrantLock;

public class Lock {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new TaskThread());
        t1.setName("张三 ");
        Thread t2 = new Thread(new TaskThread());
        t2.setName("李四 ");
        t1.start();
        Thread.sleep(100);// 保证先后顺序
        t2.start();
        Thread.sleep(100);
        // 中断
        t2.interrupt();
    }

    static class TaskThread implements Runnable {
        // 所有线程都用同一把锁
        static ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {
            try {
                //lock.lockInterruptibly();
                System.out.println(Thread.currentThread().getName() + " 吃苹果");
                Thread.sleep(5 * 1000);
                System.out.println(Thread.currentThread().getName() + " 吃香蕉");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " 被中断了，没吃了");
            } finally {
                lock.unlock();
            }
        }
    }
}