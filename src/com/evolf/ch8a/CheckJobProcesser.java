package com.evolf.ch8a;

import com.evolf.ch5.blockingqueue.ItemVo;

import java.util.concurrent.DelayQueue;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：任务完成后,在一定的时间供查询，之后为释放资源节约内存，需要定期处理过期的任务
 */
public class CheckJobProcesser {
    private static DelayQueue<ItemVo<String>> queue 
    	= new DelayQueue<ItemVo<String>>();//存放已完成任务等待过期的队列
    
	//单例模式------
	private CheckJobProcesser() {}
	
	private static class ProcesserHolder{
		public static CheckJobProcesser processer = new CheckJobProcesser();
	}
	
	public static CheckJobProcesser getInstance() {
		return ProcesserHolder.processer;
	}
	//单例模式------    
    
    //处理队列中到期任务的实行
    private static class FetchJob implements Runnable{

		@Override
		public void run() {
			while(true) {
				try {
					//拿到已经过期的任务（采用延时队列，延时时间到才能从队列中获取）
					// take（阻塞） - remove（异常） - poll（返回） 从头部获取并移除   peek-从头部获取，不移除（只做检查有没有）
					//使用阻塞方法，如果未取到会一直阻塞等待
					ItemVo<String> item = queue.take();
					String jobName =  (String)item.getDate();
					//根据任务名移除
					PendingJobPool.getMap().remove(jobName);
					System.out.println(jobName+" is out of date,remove from map!");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}			
		}
    }
    
    /*任务完成后，放入队列，经过expireTime时间后，从整个框架中移除*/
    public void putJob(String jobName,long expireTime) {
    	ItemVo<String> item = new ItemVo<String>(expireTime,jobName);
		//放入队列  offer 有返回值 插入到尾部（返回成功、失败）
    	queue.offer(item);
    	System.out.println("Job["+jobName+"已经放入了过期检查缓存，过期时长："+expireTime);
    }
    
    static {
    	Thread thread = new Thread(new FetchJob());
    	thread.setDaemon(true);
    	thread.start();
    	System.out.println("开启任务过期检查守护线程................");
    }
    
    
}
