package com.evolf.ch4.tranfer.service;

import com.evolf.ch4.tranfer.UserAccount;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：银行转账动作接口
 */
public interface ITransfer {
    void transfer(UserAccount from, UserAccount to, int amount) 
    		throws InterruptedException;
}
