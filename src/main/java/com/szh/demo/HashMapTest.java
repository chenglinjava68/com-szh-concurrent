package com.szh.demo;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by zhihaosong on 16-12-27.
 */
public class HashMapTest {
    public static void iteratorRemove() {
        List<String> students = new ArrayList<String>();
        students.add("aa");
        students.add("bb");
        students.add("cc");
        students.add("dd");
        students.add("aa");
        students.add("bb");
        students.add("aa");
        students.add("ee");
        students.add("ff");
        students.add("gg");

        System.out.println(students);
        Iterator<String> stuIter = students.iterator();
        while (stuIter.hasNext()) {
            String student = stuIter.next();
            if ("aa".equals(student))
                //这里要使用Iterator的remove方法移除当前对象，如果使用List的remove方法，则同样会出现ConcurrentModificationException
                stuIter.remove();
        }
        System.out.println(students);
    }

    public static void main(String[] args) {
        HashMap map = new HashMap();
        iteratorRemove();
        /*   1.倒过来遍历list
                [java]
        for (int i = list.size()-1; i >=0; i--)
        {
            int item = list.get(i);
            if (item == 3)
            {
                list.remove(item);
            }
        }

        for (int i = list.size()-1; i >=0; i--)
        {
            int item = list.get(i);
            if (item == 3)
            {
                list.remove(item);
            }
        }

        2.每移除一个元素以后再把i移回来
                [java]
        for (int i = 0; i < list.size(); i++)
        {
            int item = list.get(i);
            if (item == 3)
            {
                list.remove(item);
                i=i-1;
            }
        }

        for (int i = 0; i < list.size(); i++)
        {
            int item = list.get(i);
            if (item == 3)
            {
                list.remove(item);
                i=i-1;
            }
        }

        3.使用iterator.remove()方法删除
                [java]
        for (Iterator<Integer> iter = list.iterator(); iter.hasNext();)
        {
            int item = iter.next();
            if (item == 3)
            {
                list.remove(item);
            }
        }

        for (Iterator<Integer> iter = list.iterator(); iter.hasNext();)
        {
            int item = iter.next();
            if (item == 3)
            {
                list.remove(item);
            }
        }*/
    }
}
