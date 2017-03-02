package com.szh.concurrent.taskSchedule;
/**
 * Java线程：线程的调度-优先级
 * Created by zhihaosong on 17-2-22.
 */
public class TestPriority {
    public static void main(String[] args) {
        Thread t1 = new MyThread1();
        Thread t2 = new Thread(new MyRunnable());
        t1.setPriority(10);
        t2.setPriority(1);

        t2.start();
        t1.start();
    }
}

class MyThread1 extends Thread {
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("线程1第" + i + "次执行！");
                      /*  try {
                                Thread.sleep(100);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }*/
        }
    }
}

class MyRunnable implements Runnable {
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("线程2第" + i + "次执行！");
                      /*  try {
                                Thread.sleep(100);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }*/
        }
    }
}