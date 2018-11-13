package com.xiangxue.ch2;

import com.xiangxue.tools.SleepTools;
/**
 * 
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：演示Join（）方法的使用
 */
public class UseJoin {
	
    static class JumpQueue implements Runnable {
        private Thread thread;

        public JumpQueue(Thread thread) {
            this.thread = thread;
        }

        public void run() {
            try {
                thread.join();//调用传入线程的join方法，必须等这个方法返回后，当前线程才能继续执行
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }

    public static void main(String[] args) throws Exception {
        Thread previous = Thread.currentThread();//现在previous是主线程
        for (int i = 0; i < 10; i++) {
            // 每个线程拥有前一个线程的引用，需要等待前一个线程终止，才能从等待中返回
            Thread thread =
                    new Thread(new JumpQueue(previous), String.valueOf(i));
            System.out.println(previous.getName()+" jump a queue the thread:"
                    +thread.getName());
            thread.start();
            previous = thread;
        }

        SleepTools.second(2);//让主线程休眠2秒
        System.out.println(Thread.currentThread().getName() + " terminate.");
    }
}
