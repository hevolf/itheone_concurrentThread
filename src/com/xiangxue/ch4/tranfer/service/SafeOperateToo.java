package com.xiangxue.ch4.tranfer.service;

import java.util.Random;

import com.xiangxue.ch4.tranfer.UserAccount;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：不会产生死锁的安全转账第二种方法
 */
public class SafeOperateToo implements ITransfer {

    @Override
    public void transfer(UserAccount from, UserAccount to, int amount)
            throws InterruptedException {
        Random r = new Random();
        while(true){
            if(from.getLock().tryLock()){
                System.out.println(Thread.currentThread().getName()
                        +" get "+from.getName());
                //Thread.sleep(100);
                try{
                    if(to.getLock().tryLock()){
                        try{
                            System.out.println(Thread.currentThread().getName()
                                    +" get "+to.getName());
                            from.flyMoney(amount);
                            to.addMoney(amount);
                            System.out.println(from);
                            System.out.println(to);
                            break;
                        }finally {
                            to.getLock().unlock();
                        }
                    }
                }finally {
                    from.getLock().unlock();
                }
            }
            //Thread.sleep(r.nextInt(10));
        }

    }
}
