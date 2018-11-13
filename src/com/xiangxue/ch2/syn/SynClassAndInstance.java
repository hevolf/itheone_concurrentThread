package com.xiangxue.ch2.syn;

import com.xiangxue.tools.SleepTools;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：演示实例锁和类锁是不同的，两者可以并行,锁的实例不一样，也是可以并行的
 */
public class SynClassAndInstance {
	
    private static class SynClass extends Thread{
        @Override
        public void run() {
            System.out.println("TestClass is running...");
            synClass();
        }
    }

    private static class InstanceSyn implements Runnable{
        private SynClassAndInstance SynClassAndInstance;

        public InstanceSyn(SynClassAndInstance SynClassAndInstance) {
            this.SynClassAndInstance = SynClassAndInstance;
        }

        @Override
        public void run() {
            System.out.println("TestInstance is running..."+SynClassAndInstance);
            SynClassAndInstance.instance();
        }
    }

    private static class Instance2Syn implements Runnable{
        private SynClassAndInstance SynClassAndInstance;

        public Instance2Syn(SynClassAndInstance SynClassAndInstance) {
            this.SynClassAndInstance = SynClassAndInstance;
        }
        @Override
        public void run() {
            System.out.println("TestInstance2 is running..."+SynClassAndInstance);
            SynClassAndInstance.instance2();
        }
    }

    private synchronized void instance(){
        SleepTools.second(3);
        System.out.println("synInstance is going..."+this.toString());
        SleepTools.second(3);
        System.out.println("synInstance ended "+this.toString());
    }

    private synchronized void instance2(){
        SleepTools.second(3);
        System.out.println("synInstance2 is going..."+this.toString());
        SleepTools.second(3);
        System.out.println("synInstance2 ended "+this.toString());
    }

    private static synchronized void synClass(){
        SleepTools.second(1);
        System.out.println("synClass going...");
        SleepTools.second(1);
        System.out.println("synClass end");
    }

    public static void main(String[] args) {
        SynClassAndInstance synClassAndInstance = new SynClassAndInstance();
        Thread t1 = new SynClass();
        Thread t2 = new Thread(new InstanceSyn(synClassAndInstance));
        Thread t3 = new Thread(new Instance2Syn(synClassAndInstance));
//        SynClassAndInstance synClassAndInstance2 = new SynClassAndInstance();
//        Thread t4 = new Thread(new InstanceSyn(synClassAndInstance2));
        t2.start();
        t3.start();
//        t4.start();
        SleepTools.second(1);
        t1.start();
    }
}
