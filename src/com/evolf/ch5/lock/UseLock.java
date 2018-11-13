package com.evolf.ch5.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：演示Lock的标准用法
 */
public class UseLock {
	public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            //do business......
        } finally {
            lock.unlock();
        }
	}

}
