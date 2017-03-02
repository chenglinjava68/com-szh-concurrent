package com.szh.algorithm.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhihaosong on 17-1-3.
 */
public class RandomTest {
    //从长度为n的数组中随机的选择m个整数
    /*在Java中，随机数的产生取决于种子，随机数和种子之间的关系遵循以下两个规则：
      1.种子不同，产生不同的随机数。多次运行结果也不同
      2.种子相同，即使实例不同也产生相同的随机数。无论运行多少次结果都一样
      Random类的默认种子（无参构造）是System.nanoTime()的返回值
      （JDK1.5版本以前默认种子是System.currentTimeMillis()的返回值），
      这个值是距离某一个固定时间点的纳秒数，
      不同操作系统和硬件有不同的固定时间点，
      也就是不同的操作系统纳秒值是不同的，而同一操作系统的纳秒值也会不同，随机数自然也就不同了。
     */
    public static int[] selectM(int[] arr, int m) {
        int len = arr.length;
        if (m > arr.length)
            throw new RuntimeException("xxxxx");
        int[] res = new int[m];
        for (int i = 0; i < m; i++) {
            int randomIndex = len - 1 - new Random().nextInt(len - i);
            res[i] = arr[randomIndex];
            int tmp = arr[randomIndex];
            arr[randomIndex] = arr[i];
            arr[i] = tmp;
        }
        return res;
    }


    public static void main(String[] args) {
        List<String> arrlist = new ArrayList<String>();
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i;
            arrlist.add(i + "");
        }
        for (int n : selectM(arr, 40)) {
            System.out.print(n + "\t");
        }

        final Random r = new Random();

        final Random rd = new Random(1000);
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(r.nextInt());
                System.out.println(rd.nextInt());
                System.out.println(r.nextInt(100));
                System.out.println(rd.nextInt(100) + "\n======");
                //  System.out.println(Math.random()*100);
            }
        }, 2, 2, TimeUnit.SECONDS);
        String str = "aaaabbbbbb";
        System.out.println(str.contains("ab"));
    }
}
