package com.xiangxue.ch2.syn;

import com.xiangxue.tools.SleepTools;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：演示synchronized关键字的作用，不加和加上的区别，最后打印的线程应该打印100000
 */
public class SynTest {
	
	private int age = 100000;//初始100000
	
    private static class TestThread extends Thread{
    	
    	private SynTest synTest;
    	
    	public TestThread(SynTest synTest,String name) {
    		super(name);
    		this.synTest = synTest;
		}
    	
    	@Override
	    public void run() {
    		for(int i=0;i<100000;i++) {//递增100000
    			synTest.test();
    		}
			System.out.println(Thread.currentThread().getName()
					+" age =  "+synTest.getAge());
	    }
    }
    
	public synchronized void test() {
		age++;
	}
	
	public void test2() {
		synchronized(this){
			age--;
		}
	}
	
	public int getAge() {
		return age;
	}
	

	public static void main(String[] args) throws InterruptedException {
		SynTest synTest = new SynTest();
		Thread endThread = new TestThread(synTest,"endThread");
		endThread.start();
		for(int i=0;i<100000;i++) {//递减100000
			synTest.test2();
		}
		System.out.println(Thread.currentThread().getName()
				+" age =  "+synTest.getAge());		

	}
}
