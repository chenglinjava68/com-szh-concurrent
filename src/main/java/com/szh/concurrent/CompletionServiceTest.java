package com.szh.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by zhihaosong on 16-6-23.
 */
public class CompletionServiceTest {
    static int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 8, 123, 334, 5, 666, 777, 888};

    static class Task implements Callable<String> {
        private int i;

        public Task(int i) {
            this.i = i;
        }

        @Override
        public String call() throws Exception {
            //Thread.sleep(10000);
            return Thread.currentThread().getName() + "执行完任务：" + i+"\t"+nums[i];
        }
    }

    public static void main(String[] args) {
        testUseFuture();
    }

    private static void testUseFuture() {
        int numThread = 5;
        ExecutorService executor = Executors.newFixedThreadPool(numThread);
        List<Future<String>> futureList = new ArrayList<Future<String>>();
        for (int i = 0; i < numThread; i++) {
            Future<String> future = executor.submit(new CompletionServiceTest.Task(i));
            futureList.add(future);
        }

        while (numThread > 0) {
            for (Future<String> future : futureList) {
                String result = null;
                try {
                    result = future.get(0, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    //超时异常直接忽略
                }
                if (null != result) {
                    futureList.remove(future);
                    numThread--;
                    System.out.println(result);
                    //此处必须break，否则会抛出并发修改异常。（也可以通过将futureList声明为CopyOnWriteArrayList类型解决）
                    break;
                }
            }
        }
    }
}
