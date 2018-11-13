package com.xiangxue.ch8.vo;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：要求框架使用者实现的任务接口，因为任务的性质在调用时才知道，
 *所以传入的参数和方法的返回值均使用泛型
 */
public interface ITaskProcesser<T, R> {
	 TaskResult<R> taskExecute(T data);
}
