package com.szh.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by zhihaosong on 16-5-18.
 * 反射机制
 */
public class MethodTest {


    public static void main(String[] args) {
        try {
            A a = new A();
            Class<?> clz = Class.forName("com.szh.reflect.B");
            Object o = clz.newInstance();
            Method m = clz.getDeclaredMethod("foo", String.class);
            Constructor b = clz.getConstructor();
            for (int i = 0; i < 16; i++) {
                m.invoke(o, Integer.toString(i));
            }
            System.out.println(b.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class A {
    public void foo(String name) {
        System.out.println("Hello, " + name);
    }
}

class B {
    public B() {
        System.out.println("com.szh.reflect.B constructor");
    }

    public void foo(String name) {
        System.out.println("com.szh.reflect.B-Hello, " + name);
    }
}