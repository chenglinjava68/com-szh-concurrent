package com.szh.algorithm;

import java.util.List;

/**
 * Created by zhihaosong on 17-2-20.
 * http://www.cnblogs.com/shuaiwhu/archive/2012/04/27/2473788.html
 * 从数组中取出n个元素的所有组合（递归实现）
 */
public class combine {
    public void combination(List<Integer> list, int start, List<Integer> result, int index, int num) {
        for (int i = start; i < list.size() + 1 - index; i++) {
            result.set(index - 1, i);
            if (index - 1 == 0) {
                for (int j = num - 1; j >= 0; j--) {
                    System.out.print(list.get(result.get(j)) + "\t");
                }
                System.out.println();
            } else {
                combination(list, i + 1, result, index - 1, num);
            }
        }
    }

}
