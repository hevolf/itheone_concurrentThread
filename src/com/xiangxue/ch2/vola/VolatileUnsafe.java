package com.xiangxue.ch2.vola;

import com.xiangxue.tools.SleepTools;

/**
 * 
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：演示violate无法提供操作的原子性
 */
public class VolatileUnsafe {
	
	private static class VolatileVar implements Runnable {
		
	    private volatile int a = 0;

	    @Override
	    public void run() {
            a = a + 1;
            System.out.println(Thread.currentThread().getName() + ":----" + a);
            SleepTools.ms(100);
            a = a + 1;
            System.out.println(Thread.currentThread().getName() + ":----" + a);
	    }
	}
	
    public static void main(String[] args) {

    	VolatileVar v = new VolatileVar();

        Thread t1 = new Thread(v);
        Thread t2 = new Thread(v);
        Thread t3 = new Thread(v);
        Thread t4 = new Thread(v);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

}
