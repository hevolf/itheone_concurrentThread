package com.xiangxue.ch4;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：演示普通账户的死锁和解决
 */
public class NormalDeadLock {
    private static Object valueFirst = new Object();//第一个锁
    private static Object valueSecond = new Object();//第二个锁

    //先拿第一个锁，再拿第二个锁
    private static void fisrtToSecond() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        synchronized (valueFirst){
            System.out.println(threadName+" get valueSecond");
            Thread.sleep(100);
            synchronized (valueSecond){
                System.out.println(threadName+" get valueFirst");
            }
        }
    }

    //先拿第二个锁，再拿第一个锁
    private static void SecondToFisrt() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        synchronized (valueSecond){
            System.out.println(threadName+" get valueSecond");
            Thread.sleep(100);
            synchronized (valueFirst){
                System.out.println(threadName+" get valueFirst");
            }
        }
    }

    private static class TestThread extends Thread{

        private String name;

        public TestThread(String name) {
            this.name = name;
        }

        public void run(){
            Thread.currentThread().setName(name);
            try {
                SecondToFisrt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("TestDeadLock");
        TestThread testThread = new TestThread("SubTestThread");
        testThread.start();
        try {
            fisrtToSecond();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
