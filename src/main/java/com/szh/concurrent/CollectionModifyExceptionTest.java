package com.szh.concurrent;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionModifyExceptionTest {
    public static void main(String[] args) {
        Collection users = new CopyOnWriteArrayList();
        //new ArrayList();
        users.add(new User("szh", 28));
        users.add(new User("ayb", 25));
        users.add(new User("mgq", 31));
        Iterator itrUsers = users.iterator();
        while (itrUsers.hasNext()) {
            System.out.println("aaaa");
            User user = (User) itrUsers.next();
            if ("szh".equals(user.getName())) {
                users.remove(user);
                //itrUsers.remove();
            } else {
                System.out.println(user);
            }
        }
    }
}	 
