package com.itheone.ch1.safeend;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：如何安全的中断线程
 */
public class EndThread {
	private static class UseThread extends Thread{
		public UseThread(String name) {
			super(name);
		}

		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName+" interrrupt flag ="+isInterrupted());
			while(!isInterrupted()){
				//while(!Thread.interrupted()){  //如果线程中断标识位为true，会将中断标志位由true改为false，并返回true
				//while(true){
				System.out.println(threadName+" is running");
				System.out.println(threadName+"inner interrrupt flag ="
						+isInterrupted());
			}
			System.out.println(threadName+" interrrupt flag ="+isInterrupted());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread endThread = new UseThread("endThread");
		endThread.start();
		Thread.sleep(20);
		//中断线程，其实设置线程的标识位true
		endThread.interrupt();

	}
}
