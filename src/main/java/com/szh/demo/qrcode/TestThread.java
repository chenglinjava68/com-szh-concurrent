package com.szh.demo.qrcode;

/**
 * Created by zhihaosong on 16-6-7.
 */
public class TestThread implements Runnable {

    private String name;
    private Object prev;
    private Object self;

    private TestThread(String name, Object prev, Object self) {
        this.name = name;
        this.prev = prev;
        this.self = self;
    }

    @Override
    public void run() {
       /* int count = 10;
        while (count > 0) {
            synchronized (prev) {
                synchronized (self) {
                    System.out.print(name+count+"\t");
                    count--;
                    try {
                        self.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    prev.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }*/
            int count = 10;
            while (count > 0) {
                synchronized (prev) {
                    synchronized (self) {
                        System.out.print(name);
                        count--;
                        try{
                            Thread.sleep(1);
                        }
                        catch (InterruptedException e){
                            e.printStackTrace();
                        }

                        self.notify();
                    }
                    try {
                        prev.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
    }
    public static void main(String[] args) throws Exception {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        TestThread pa = new TestThread("A", c, a);
        TestThread pb = new TestThread("B", a, b);
        TestThread pc = new TestThread("C", b, c);


        new Thread(pa).start();
        new Thread(pb).start();
        new Thread(pc).start();
    }
}
