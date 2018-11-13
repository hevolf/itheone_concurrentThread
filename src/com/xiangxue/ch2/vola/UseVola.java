package com.xiangxue.ch2.vola;

import com.xiangxue.tools.SleepTools;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：演示volatile的使用
 */
public class UseVola {
	
	private volatile int age = 100000;
	
    private static class TestThread extends Thread{
    	
    	private UseVola synTest;
    	
    	public TestThread(UseVola synTest,String name) {
    		super(name);
    		this.synTest = synTest;
		}
    	
    	@Override
	    public void run() {
    		for(int i=0;i<100000;i++) {
    			synTest.test();
    		}
			System.out.println(Thread.currentThread().getName()
					+" age =  "+synTest.getAge());
	    }
    }
    
	public void test() {
		age++;
	}
	
	public int getAge() {
		return age;
	}
	

	public static void main(String[] args) throws InterruptedException {
		UseVola synTest = new UseVola();
		Thread endThread = new TestThread(synTest,"endThread");
		endThread.start();
		SleepTools.ms(2);
		System.out.println(Thread.currentThread().getName()
				+" age =  "+synTest.getAge());		

	}
}
