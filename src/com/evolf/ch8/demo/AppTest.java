package com.evolf.ch8.demo;

import java.util.List;
import java.util.Random;

import com.evolf.ch8.PendingJobPool;
import com.evolf.ch8.vo.TaskResult;
import com.evolf.tools.SleepTools;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：模拟一个应用程序，提交工作和任务，并查询任务进度
 */
public class AppTest {
	
	private final static String JOB_NAME = "计算数值";
	private final static int JOB_LENGTH = 1000;
	
	//查询任务进度的线程
	private static class QueryResult implements Runnable{
		
		private PendingJobPool pool;

		public QueryResult(PendingJobPool pool) {
			super();
			this.pool = pool;
		}

		@Override
		public void run() {
			int i=0;
			while(i<350) {
				List<TaskResult<String>> taskDetail = pool.getTaskDetail(JOB_NAME);
				if(!taskDetail.isEmpty()) {
					System.out.println(pool.getTaskProgess(JOB_NAME));
					System.out.println(taskDetail);					
				}
				SleepTools.ms(100);
				i++;
			}
		}
		
	}

	public static void main(String[] args) {
		MyTask myTask = new MyTask();
		PendingJobPool pool = PendingJobPool.buildPool();
		pool.registerJob(JOB_NAME, JOB_LENGTH, myTask,1000*5);
		Random r = new Random();
		for(int i=0;i<JOB_LENGTH;i++) {
			pool.putTask(JOB_NAME, r.nextInt(1000));
		}
		Thread t = new Thread(new QueryResult(pool));
		t.start();
	}
}
