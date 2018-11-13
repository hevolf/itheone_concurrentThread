package com.xiangxue.ch6;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.xiangxue.tools.SleepTools;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：线程池的使用范例
 */
public class UseThreadPool {
    static class Worker implements Runnable
    {
        private String taskName;
        private Random r = new Random();

        public Worker(String taskName){
            this.taskName = taskName;
        }

        public String getName() {
            return taskName;
        }

        @Override
        public void run(){
            System.out.println(Thread.currentThread().getName()
            		+" process the task : " + taskName);
            SleepTools.ms(r.nextInt(100)*5);
        }
    }

    public static void main(String[] args)
    {
        ExecutorService threadPool = new ThreadPoolExecutor(2, 4, 3,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        //ExecutorService cachePool = Executors.newCachedThreadPool();
        //ExecutorService fixPool = Executors.newFixedThreadPool(2);
        //ExecutorService singlePool = Executors.newSingleThreadExecutor();
        //ExecutorService forkjoinPool = Executors.newWorkStealingPool();
        for (int i = 0; i <= 6; i++)
        {
            Worker worker = new Worker("worker " + i);
            System.out.println("A new task has been added : " + worker.getName());
            threadPool.execute(worker);
        }
        threadPool.shutdown();
    }
}
