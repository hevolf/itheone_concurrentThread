package com.evolf.ch7_ThreadSafe.tranfer.service;

import com.evolf.ch7_ThreadSafe.tranfer.UserAccount;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：不会产生死锁的安全转账
 */
public class SafeOperate implements ITransfer {
	private static Object tieLock = new Object();//加时赛锁

    @Override
    public void transfer(UserAccount from, UserAccount to, int amount)
            throws InterruptedException {
    	
    	int fromHash = System.identityHashCode(from);//原始hash值，冲突概率千万分之1
    	int toHash = System.identityHashCode(to);
    	//先锁hash小的那个 将加锁顺序由不确定性改成确定性
    	if(fromHash<toHash) {
            synchronized (from){
                System.out.println(Thread.currentThread().getName()
                		+" get"+from.getName());
                Thread.sleep(100);
                synchronized (to){
                    System.out.println(Thread.currentThread().getName()
                    		+" get"+to.getName());
                    from.flyMoney(amount);
                    to.addMoney(amount);
                }
            }    		
    	}else if(toHash<fromHash) {
            synchronized (to){
                System.out.println(Thread.currentThread().getName()
                		+" get"+to.getName());
                Thread.sleep(100);
                synchronized (from){
                    System.out.println(Thread.currentThread().getName()
                    		+" get"+from.getName());
                    from.flyMoney(amount);
                    to.addMoney(amount);
                }
            }    		
    	}else {//解决hash冲突的方法=时，A,B先竞争tieLock，必定只有一个人能获取锁
    		synchronized (tieLock) {
				synchronized (from) {
					synchronized (to) {
	                    from.flyMoney(amount);
	                    to.addMoney(amount);						
					}
				}
			}
    	}
    	
    }
}
