package com.szh.concurrent;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountdownLatchTest {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		final CountDownLatch cdOrder = new CountDownLatch(1);
		final CountDownLatch cdAnswer = new CountDownLatch(3);		
		for(int i=0;i<3;i++){
			Runnable runnable = new Runnable(){
					public void run(){
					try {
						System.out.println(Thread.currentThread().getName() +
								"await before");
						cdOrder.await();
						System.out.println(Thread.currentThread().getName() +
						"await ing");
						Thread.sleep((long)(Math.random()*10000));	
						System.out.println(Thread.currentThread().getName() +
								"sleep after");
						cdAnswer.countDown();						
					} catch (Exception e) {
						e.printStackTrace();
					}				
				}
			};
			service.execute(runnable);
		}		
		try {
			Thread.sleep((long)(Math.random()*10000));
		
			System.out.println( Thread.currentThread().getName() +
					"main thread");
			cdOrder.countDown();
			System.out.println(Thread.currentThread().getName() +
			"main thread countdown");
			cdAnswer.await();
			System.out.println(Thread.currentThread().getName() +
			"main thread cd await end");
		} catch (Exception e) {
			e.printStackTrace();
		}				
		service.shutdown();
	}
}
