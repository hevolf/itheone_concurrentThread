package com.evolf.ch2.tools;

import com.evolf.tools.SleepTools;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：演示CountDownLatch，有5个初始化的线程，6个扣除点，
 *扣除完毕以后，主线程和业务线程才能继续自己的工作
 */
public class UseCountDownLatch {
	
	static CountDownLatch latch = new CountDownLatch(6);

	//初始化线程(只有一步，有4个)
    private static class InitThread implements Runnable{

        @Override
        public void run() {
        	System.out.println("Thread_"+Thread.currentThread().getId()
        			+" ready init work......" + new Date());
        	latch.countDown();//初始化线程完成工作了，countDown方法只扣减一次；
            for(int i =0;i<2;i++) {
            	System.out.println("Thread_"+Thread.currentThread().getId()
            			+" ........continue do its work" + new Date());
            }
        }
    }
    
    //业务线程
    private static class BusiThread implements Runnable{

        @Override
        public void run() {
        	try {
				latch.await();//await 会和main 的await同时放行
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            for(int i =0;i<3;i++) {
            	System.out.println("BusiThread_"+Thread.currentThread().getId()
            			+" do business-----"+ new Date());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
    	//单独的初始化线程,初始化分为2步，需要扣减两次
        new Thread(new Runnable() {
            @Override
            public void run() {
            	SleepTools.ms(1);
                System.out.println("Thread_"+Thread.currentThread().getId()
            			+" ready init work step 1st......" + new Date());
                latch.countDown();//每完成一步初始化工作，扣减一次
                System.out.println("begin step 2nd......." + new Date());
                SleepTools.ms(1);
                System.out.println("Thread_"+Thread.currentThread().getId()
            			+" ready init work step 2nd......" + new Date());
                latch.countDown();//每完成一步初始化工作，扣减一次
            }
        }).start();
        new Thread(new BusiThread()).start();// await 会和main 的await同时放行
        for(int i=0;i<=3;i++){
            Thread thread = new Thread(new InitThread());
            thread.start();
        }

        latch.await();
        System.out.println("Main do ites work........ "+ new Date());
    }
}
