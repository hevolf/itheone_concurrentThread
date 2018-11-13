package com.xiangxue.ch1.safeend;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：实现接口Runnable的线程如何中断
 */
public class EndRunnable {
	
	private static class UseRunnable implements Runnable{
		
		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted()) {//改为true，线程不会中断;改为Thread.interrupted()，标志位被复位
				System.out.println(Thread.currentThread().getName()
						+ " I am implements Runnable.");
			}
			System.out.println(Thread.currentThread().getName()
					+" interrupt flag is "+Thread.currentThread().isInterrupted());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		UseRunnable useRunnable = new UseRunnable();
		Thread endThread = new Thread(useRunnable,"endThread");
		endThread.start();
		Thread.sleep(20);
		endThread.interrupt();
	}

}
