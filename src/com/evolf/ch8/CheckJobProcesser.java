package com.evolf.ch8;

import java.util.Map;
import java.util.concurrent.DelayQueue;

import com.evolf.ch7.ItemVo;
import com.evolf.ch8.vo.JobInfo;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：任务完成后,在一定的时间供查询，之后为释放资源节约内存，需要定期处理过期的任务
 */
public class CheckJobProcesser {
    private DelayQueue<ItemVo<String>> queue;//存放任务的队列

    public CheckJobProcesser(DelayQueue<ItemVo<String>> queue){
        this.queue = queue;
    }

    //处理队列中到期任务的线程
    private static class FetchJob implements Runnable{

    	private DelayQueue<ItemVo<String>> queue;
    	private Map<String,JobInfo<?>> JobInfoMap ;

		public FetchJob(DelayQueue<ItemVo<String>> queue, 
				Map<String, JobInfo<?>> jobInfoMap) {
			super();
			this.queue = queue;
			JobInfoMap = jobInfoMap;
		}

		@Override
		public void run() {
	        while(true){
	            try {
	            	ItemVo<String> item = queue.take();
	            	String jobName = (String)item.getData();
	                JobInfoMap.remove(jobName);//从缓存中移除过期的任务
	                System.out.println("Job：["+ jobName+"] is out of date,remove from JobList! ");
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
		}
    }
    
    /*任务完成后，放入队列，经过expireTime时间后，从整个框架中移除*/
    public void putJob(String jobName,long expireTime) {
    	ItemVo<String> itemTb = new ItemVo<String>(expireTime,jobName);
        queue.offer(itemTb);    
        System.out.println("任务["+jobName+"]已被放入过期检查缓存，过期时长："+expireTime+"ms");
    }
    
    /*启动检查队列的线程，设置为守护线程*/
    public void initCheck(Map<String, JobInfo<?>> jobInfoMap) {
    	Thread thread = new Thread(new FetchJob(queue,jobInfoMap));
    	thread.setDaemon(true);
    	thread.start();
    	System.out.println("开启任务过期检查守护线程...........");
    }
    
}
