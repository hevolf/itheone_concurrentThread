package com.xiangxue.ch1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 
 *@author Mark老师   {@link https://enjoy.ke.qq.com 享学课堂}
 *
 *更多课程咨询 安生老师 QQ�?669100976  VIP课程咨询 依娜老师  QQ�?2470523467
 *
 *类说明：新启线程的方式
 */
public class NewThread {
	
	/*扩展自Thread类*/
	private static class UseThread extends Thread{
		@Override
		public void run() {
			super.run();
			//do my work
			System.out.println("I am extended Thread.");
		}
	}
	
	/*实现Runnable接口*/
	private static class UseRunnable implements Runnable{
		@Override
		public void run() {
			//do my work
			System.out.println("I am implements Runnable.");
		}
	}
	
	/*实现Callable接口，允许有返回值*/
	private static class UseCallable implements Callable<String>{
		@Override
		public String call() throws Exception {
			//do my work
			System.out.println("I am implements Callable.");
			return "CallResult";
		}
	}
	
	public static void main(String[] args) 
			throws InterruptedException, ExecutionException {
		UseThread useThread = new UseThread();
		useThread.start();
		
		UseRunnable useRunnable = new UseRunnable();
		new Thread(useRunnable).start();
		
		UseCallable useCallable = new UseCallable();
		FutureTask<String> futureTask //用FutureTask包装Callable
			= new FutureTask<>(useCallable);
		new Thread(futureTask).start();//交给Thread去运行
		System.out.println("Get UseCallable result = "+futureTask.get());
		
	}

}
