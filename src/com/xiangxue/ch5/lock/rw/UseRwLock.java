package com.xiangxue.ch5.lock.rw;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.xiangxue.tools.SleepTools;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：用读写锁来实现商品服务接口
 */
public class UseRwLock implements GoodsService {
	
    private GoodsInfo goodsInfo;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock getLock = lock.readLock();//get
    private final Lock setLock = lock.writeLock();//set

    public UseRwLock(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

	@Override
	public GoodsInfo getNum() {
		getLock.lock();
        SleepTools.ms(5);
        try {
            return this.goodsInfo;
        } finally {
        	getLock.unlock();
        }
	}

	@Override
	public void setNum(int number) {
		setLock.lock();
        try {
        	SleepTools.ms(50);
            this.goodsInfo.changeNumber(number);
        } finally {
        	setLock.unlock();
        }

	}

}
