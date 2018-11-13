package com.evolf.ch5.cas;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：演示带版本戳的原子操作类
 */
public class UseAtomicStampedReference {
    static AtomicStampedReference<String> asr =
            new AtomicStampedReference("mark", 0);

    public static void main(String[] args) throws InterruptedException {
        final int oldStamp = asr.getStamp();//拿初始版本0
        final String oldReference = asr.getReference();//初始值
        System.out.println(oldReference+"============"+oldStamp);
        Thread rightStampThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+":当前变量值："
                        +oldReference + "-当前版本戳：" + oldStamp + "-"
                  + asr.compareAndSet(oldReference,
                        oldReference + "+Java", oldStamp, oldStamp + 1));
            }
        });

        Thread errorStampThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String reference = asr.getReference();//变量的最新值
                System.out.println(Thread.currentThread().getName()+":当前变量值："
                        +reference + "-当前版本戳：" + asr.getStamp() + "-"
                        + asr.compareAndSet(reference,
                        reference + "+C", oldStamp, oldStamp + 1));
            }
        });
        rightStampThread.start();
        rightStampThread.join();
        errorStampThread.start();
        errorStampThread.join();

        System.out.println(asr.getReference()+"============"+asr.getStamp());
    }
}
