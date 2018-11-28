package com.evolf.ch7_ThreadSafe.tranfer.service;

import com.evolf.ch7_ThreadSafe.tranfer.UserAccount;
import com.evolf.tools.SleepTools;

import java.util.Random;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：不会产生死锁的安全转账第二种方法，尝试拿锁
 */
public class SafeOperateToo implements ITransfer {

    @Override
    public void transfer(UserAccount from, UserAccount to, int amount)
            throws InterruptedException {
    	Random r = new Random();
    	while(true) {
    		if(from.getLock().tryLock()) {
    			try {
    				System.out.println(Thread.currentThread().getName()
    						+" get "+from.getName());
    				if(to.getLock().tryLock()) {
    					try {
    	    				System.out.println(Thread.currentThread().getName()
    	    						+" get "+to.getName());    						
    						//两把锁都拿到了
    	                    from.flyMoney(amount);
    	                    to.addMoney(amount);
    	                    break;//成功拿到两把锁时才跳出，否则一直循环
    					}finally {
    						to.getLock().unlock();
    					}
    				}
    			}finally {
    				from.getLock().unlock();
    			}
    		}
    		SleepTools.ms(r.nextInt(10));//每次不成功时 随机休眠
    	}
    }
}
