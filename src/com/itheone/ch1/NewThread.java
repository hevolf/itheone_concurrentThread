package com.itheone.ch1;

import java.util.concurrent.ExecutionException;

/**
 *@author
 *
 *类说明：如何新建线程
 */
public class NewThread {
	/*扩展自Thread类*/
	private static class UseThread extends Thread{
		@Override
		public void run() {
			super.run();
			// do my work;
			System.out.println("I am extend Thread");
		}
	}
	/*实现Runnable接口*/
	private static class UseRunnable implements Runnable{
		@Override
		public void run() {
			System.out.println("I am implements Runnable");
		}
	}

	public static void main(String[] args) 
			throws InterruptedException, ExecutionException {
		UseThread useThread = new UseThread();
		useThread.start();

		UseRunnable useRun = new UseRunnable ();
		new Thread(useRun).start();
	}
}
