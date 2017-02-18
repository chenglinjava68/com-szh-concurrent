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
        final Random rd = new Random(100);
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(r.nextInt());
                System.out.println(rd.nextInt());
                System.out.println(r.nextInt(100));
                System.out.println(rd.nextInt(100)+"\n======");
              //  System.out.println(Math.random()*100);
            }
        }, 2, 2, TimeUnit.SECONDS);
    }
}
