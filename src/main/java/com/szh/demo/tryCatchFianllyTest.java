package com.szh.demo;

/**
 * Created by zhihaosong on 17-2-20.
 * java 的异常处理中，
 * 在不抛出异常的情况下，程序执行完 try 里面的代码块之后，该方法并不会立即结束，而是继续试图去寻找该方法有没有 finally 的代码块，
 * 如果没有 finally 代码块，整个方法在执行完 try 代码块后返回相应的值来结束整个方法；
 * 如果有 finally 代码块，此时程序执行到 try 代码块里的 return 语句之时并不会立即执行 return，而是先去执行 finally 代码块里的代码，
 * 若 finally 代码块里没有 return 或没有能够终止程序的代码，程序将在执行完 finally 代码块代码之后再返回 try 代码块执行 return 语句来结束整个方法；
 * 若 finally 代码块里有 return 或含有能够终止程序的代码，方法将在执行完 finally 之后被结束，不再跳回 try 代码块执行 return。
 */
public class tryCatchFianllyTest {

    public static int fun(int n) {
        try {
            n++;
            return n;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            n++;
        }
        return n;
    }


    public int fun1(int n) {
        try {
            n++;
        } catch (Exception e) {

        } finally {
            n++;
        }
        return n;
    }

    class User {
        private int age;
    }

    public User fun2() {
        User user = new User();
        try {
            user.age++;
        } catch (Exception e) {

        } finally {
            user.age++;
        }
        return user;
    }

    public User fun3() {
        User user = new User();
        try {
            user.age++;
            return user;
        } catch (Exception e) {

        } finally {
            user.age++;
        }
        return user;
    }

    public int test2() {
        int i = 0;
        try {
            i = 10 / 2;
            System.out.println("i=" + i);
            return i;
        } catch (Exception e) {
            System.out.println(" -- Exception --");
            return i;
        } finally {
            i++;
            System.out.println(" -- finally --");
            // System.exit(0);
            return i;
        }
    }

    public static void main(String[] args) {
        tryCatchFianllyTest tryCatchFianllyTest = new tryCatchFianllyTest();
        System.out.println("fun:\t" + tryCatchFianllyTest.fun(0));
        System.out.println();

        System.out.println(tryCatchFianllyTest.test2());
        System.out.println();

        System.out.println("fun1:\t" + tryCatchFianllyTest.fun1(0));
        System.out.println();

        System.out.println("fun2:\t" + tryCatchFianllyTest.fun2().age);
        System.out.println();

        System.out.println("fun3:\t" + tryCatchFianllyTest.fun3().age);
        System.out.println();
    }
}
