package com.szh.demo;

import java.util.Set;
import java.util.HashSet;

public class PersonTest {

    public static void main(String[] args) {
        Set<Person> ps = new HashSet();
        Person p = new Person("szh", 25);

        ps.add(p);
        p.name = "aw";
        ps.remove(p);
        System.out.println(ps.size());
    }
}