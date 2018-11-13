package com.xiangxue.ch3.tools;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：演示CyclicBarrier用法,共5个子线程，他们全部完成工作后，交出自己结果，
 *再被统一释放去做自己的事情，而交出的结果被另外的线程拿来拼接字符串
 */
public class UseCyclicBarrier {
    private static CyclicBarrier barrier = new CyclicBarrier(5, new CollectThread());
    private static ConcurrentHashMap<String,Long> resultMap
            = new ConcurrentHashMap<>();//存放子线程工作结果的容器

    public static void main(String[] args) {
        for(int i=0;i<=4;i++){
            Thread thread = new Thread(new SubThread());
            thread.start();
        }

    }

    private static class CollectThread implements Runnable{

        @Override
        public void run() {
            StringBuilder result = new StringBuilder();
            for(Map.Entry<String,Long> workResult:resultMap.entrySet()){
            	result.append("["+workResult.getValue()+"]");
            }
            System.out.println(" the result = "+ result);
            System.out.println("do other business........");
        }
    }

    private static class SubThread implements Runnable{

        @Override
        public void run() {
        	long id = Thread.currentThread().getId();
            resultMap.put(Thread.currentThread().getId()+"",id);
            Random r = new Random();
            try {
                if(r.nextBoolean()) {
                	Thread.sleep(1000+id);
                	System.out.println("Thread_"+id+" ....do something ");
                }
                barrier.await();
            	Thread.sleep(1000+id);
                System.out.println("Thread_"+id+" ....do its business ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
