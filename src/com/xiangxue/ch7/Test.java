package com.xiangxue.ch7;

import java.util.concurrent.DelayQueue;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：延时队列测试程序
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<ItemVo<Order>> queue = new DelayQueue<ItemVo<Order>>();

        new Thread(new PutOrder(queue)).start();
        new Thread(new FetchOrder(queue)).start();

        for(int i=1;i<15;i++){
            Thread.sleep(500);
            System.out.println(i*500);
        }
    }
}
