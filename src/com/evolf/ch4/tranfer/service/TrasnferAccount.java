package com.evolf.ch4.tranfer.service;

import com.evolf.ch4.tranfer.UserAccount;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：不安全的转账动作的实现
 */
public class TrasnferAccount implements ITransfer {
    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) 
    		throws InterruptedException {
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
    }
}
