package com.evolf.ch3.tools;

import java.util.concurrent.CountDownLatch;

import com.evolf.tools.SleepTools;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：演示CountDownLatch用法，共5个初始化子线程，6个闭锁扣除点，扣除完毕后，主线程和业务线程才能继续执行
 */
public class UseCountDownLatch {
	
    static CountDownLatch latch = new CountDownLatch(6);

    private static class InitThread implements Runnable{

        @Override
        public void run() {
        	System.out.println("Thread_"+Thread.currentThread().getId()
        			+" ready init work......");
            latch.countDown();
            for(int i =0;i<2;i++) {
            	System.out.println("Thread_"+Thread.currentThread().getId()
            			+" ........continue do its work");
            }
        }
    }
    
    private static class BusiThread implements Runnable{

        @Override
        public void run() {
            try {
				latch.await();
			} catch (InterruptedException e) {
			}
            for(int i =0;i<3;i++) {
            	System.out.println("BusiThread_"+Thread.currentThread().getId()
            			+" do business-----");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
            	SleepTools.ms(1);
                System.out.println("Thread_"+Thread.currentThread().getId()
            			+" ready init work step 1st......");
                latch.countDown();
                System.out.println("begin step 2nd.......");
                SleepTools.ms(1);
                System.out.println("Thread_"+Thread.currentThread().getId()
            			+" ready init work step 2nd......");
                latch.countDown();
            }
        }).start();
        new Thread(new BusiThread()).start();
        for(int i=0;i<=3;i++){
            Thread thread = new Thread(new InitThread());
            thread.start();
        }

        latch.await();
        System.out.println("Main do ites work........");
    }
}
