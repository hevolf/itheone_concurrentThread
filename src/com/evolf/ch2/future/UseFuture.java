package com.evolf.ch2.future;

import com.evolf.tools.SleepTools;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：演示Future等的使用
 */
public class UseFuture {
	
	/*实现Callable接口，允许有返回值*/
	private static class UseCallable implements Callable<Integer>{

		private int sum;
		@Override
		public Integer call() throws Exception {
			System.out.println("Callable子线程开始计算");
			Thread.sleep(2000);
			for(int i=0;i<5000;i++) {
				sum = sum+i;
			}
			System.out.println("Callable子线程计算完成，结果="+sum);
			return sum;
		}

	}
	
	public static void main(String[] args) 
			throws InterruptedException, ExecutionException {
		
		UseCallable useCallable = new UseCallable();
		FutureTask<Integer> futureTask = new FutureTask<Integer>(useCallable);
		new Thread(futureTask).start();
		Random r = new Random();
		SleepTools.second(1);
		//若没计算完  futureTask.get()会 awaitDone()
		System.out.println("Get UseCallable result = "+futureTask.get() + new Date());

		if(r.nextBoolean()) {//随机决定是获得结果还是终止任务
			System.out.println("Get UseCallable result = "+futureTask.get());
		}else {
			System.out.println("中断计算");//还在计算中 可以cancel
			futureTask.cancel(true);
		}
		
	}

}
