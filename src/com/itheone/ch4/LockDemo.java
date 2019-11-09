package com.itheone.ch4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Administrator
 *
 *使用显示锁的范式
 */
public class LockDemo {
	
	private Lock lock  = new ReentrantLock();
	private int count;
	
	public void increament() {
		lock.lock();
		try {
			count++;
		}finally {
			lock.unlock();
		}
	}

	//可重入（内部计数器）  ReentrantLock可看做是synchronized 在Lock接口的实现，都可重入
	public synchronized void incr2() {
		count++;
		incr2();//
	}
	//可重入
	public synchronized void test3() {
		incr2();
	}

}
