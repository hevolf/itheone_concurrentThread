package com.evolf.ch8a;

import com.evolf.ch8a.vo.ITaskProcesser;
import com.evolf.ch8a.vo.JobInfo;
import com.evolf.ch8a.vo.TaskResult;
import com.evolf.ch8a.vo.TaskResultType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 *@author Mark老师   itheone itheone
 * 框架的主体类，也是调用者主要使用的类
 */
public class PendingJobPool {
	
	//保守估计--计算密集型一般=cpu数，io型=cpu*2，保守一般相等即可
	private static final int THREAD_COUNTS = 
			Runtime.getRuntime().availableProcessors();
	//任务队列，用于保存已经提交未来得及处理的任务
	private static BlockingQueue<Runnable> taskQueue
	 = new ArrayBlockingQueue<>(5000);
	//线程池，固定大小，有界队列，使用自定义线程池（有界）
	private static ExecutorService taskExecutor = 
			new ThreadPoolExecutor(THREAD_COUNTS, THREAD_COUNTS, 60, 
					TimeUnit.SECONDS, taskQueue);
	//job的存放容器（并发容器：Map中的每个task 会并发操作次map，即：存在多线程操作次map）
	private static ConcurrentHashMap<String, JobInfo<?>> jobInfoMap
	   = new  ConcurrentHashMap<>();
	
	private static CheckJobProcesser checkJob
	 	= CheckJobProcesser.getInstance();

	//提供获取线程池中job容器（即所有注册job的集合）
	public static Map<String, JobInfo<?>> getMap(){
		return jobInfoMap;
	}
	
	//单例模式 （类预占位）------
	private PendingJobPool() {}
	
	private static class JobPoolHolder{
		public static PendingJobPool pool = new PendingJobPool();
	}
	
	public static PendingJobPool getInstance() {
		return JobPoolHolder.pool;
	}
	//单例模式------
	
	//对工作中的任务进行包装，提交给线程池使用，并处理任务的结果，写入缓存以供查询
	private static class PendingTask<T,R> implements Runnable{
		
		private JobInfo<R> jobInfo;
		private T processData;

		public PendingTask(JobInfo<R> jobInfo, T processData) {
			super();
			this.jobInfo = jobInfo;
			this.processData = processData;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			R r = null;
			//从提交的job中获取处理该task的处理器（预先定义的数据处理方法，在job注册时进行初始化） （该job肯定预先注册过）
			ITaskProcesser<T,R> taskProcesser =
					(ITaskProcesser<T, R>) jobInfo.getTaskProcessed();
			TaskResult<R> result = null;
			
			try {
				//调用业务人员实现的具体方法
				result = taskProcesser.taskExecute(processData);
				//要做检查，防止开发人员处理不当
				if (result == null) {
					result = new TaskResult<R>(TaskResultType.Exception, r, 
							"result is null");
				}
				if (result.getResultType() == null) {
					if (result.getReason() == null) {
						result = new TaskResult<R>(TaskResultType.Exception, r, "reason is null");
					} else {
						result = new TaskResult<R>(TaskResultType.Exception, r,
								"result is null,but reason:" + result.getReason());
					}
				} 
			} catch (Exception e) {
				e.printStackTrace();
				result = new TaskResult<R>(TaskResultType.Exception, r, 
						e.getMessage());				
			}finally {
				//保存任务处理结果
				jobInfo.addTaskResult(result,checkJob);
			}
		}
	}
	
	//根据工作名称检索工作（必要步骤）
	@SuppressWarnings("unchecked")
	private <R> JobInfo<R> getJob(String jobName){
		JobInfo<R> jobInfo = (JobInfo<R>) jobInfoMap.get(jobName);
		if(null==jobInfo) {
			throw new RuntimeException(jobName+"是个非法任务。");
		}
		return jobInfo;
	}
	
	//调用者提交工作中的任务
	public <T,R> void putTask(String jobName,T t) {
		JobInfo<R> jobInfo = getJob(jobName);
		//每提交一个task-t到job  新开一个处理线程  并将该线程任务用线程池运行taskExecutor
		PendingTask<T,R> task = new PendingTask<T,R>(jobInfo,t);
		//线程-》线程池
		taskExecutor.execute(task);
	}
	
	//调用者注册工作，如工作名，任务的处理器等等
	public <R> void registerJob(String jobName, int jobLength,
								ITaskProcesser<?, ?> taskProcesser, long expireTime) {
		JobInfo<R> jobInfo = new JobInfo(jobName,jobLength,
				taskProcesser,expireTime);
		if (jobInfoMap.putIfAbsent(jobName, jobInfo)!=null) {
			throw new RuntimeException(jobName+"已经注册了！");
		}
	}
	
	//获得每个任务的处理详情
	public <R> List<TaskResult<R>> getTaskDetail(String jobName){
		JobInfo<R> jobInfo = getJob(jobName);
		return jobInfo.getTaskDetail();
	}
	
	//获得工作的整体处理进度
	public <R> String getTaskProgess(String jobName) {
		JobInfo<R> jobInfo = getJob(jobName);
		return jobInfo.getTotalProcess();	
	}
	
}
