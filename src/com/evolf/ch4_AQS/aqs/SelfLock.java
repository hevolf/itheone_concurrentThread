package com.evolf.ch4_AQS.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：实现一个自己的类ReentrantLock(用内部类实现AbstractQueuedSynchronizer)
 */
public class SelfLock implements Lock{
	
	//state 表示获取到锁 state=1 获取到了锁 有线程占用了此锁，state=0，表示这个锁当前没有线程拿到
	private static class Sync extends AbstractQueuedSynchronizer{
		
		//是否占用
		protected boolean isHeldExclusively() {
			return getState()==1;
		}
		//尝试获取授权
		protected boolean tryAcquire(int arg) {
			//锁初始态期望为0， 更新后期望为1 即：原子操作CAS 初始没有线程占用时为0，尝试获取时传入1
			if(compareAndSetState(0,1)) {
				//CAS true 当前线程 设为独占线程
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}
		/* 释放授权 */
		protected boolean tryRelease(int arg) {
			if(getState()==0) {
				throw new UnsupportedOperationException();
			}
			setExclusiveOwnerThread(null);
			setState(0);//只有持有锁的能执行set所以不用CAS
			return true;
		}
		//condition.signal()唤醒 condition.await()的线程
		Condition newCondition() {
			return new ConditionObject();
		}
	}
	
	private final Sync sycn = new Sync();

	@Override
	public void lock() {
	    //AbstractQueuedSynchronizer 实现的方法acquire
		sycn.acquire(1); //如果失败 会调用selfInterrupt(){Thread.currentThread().interrupt()};中断当前线程
		
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sycn.acquireInterruptibly(1);
		
	}

	@Override
	public boolean tryLock() {
		return sycn.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return sycn.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() {
		sycn.release(1);
		
	}

	@Override
	public Condition newCondition() {
		return sycn.newCondition();
	}


}
