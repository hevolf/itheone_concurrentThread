package com.evolf.ch7;

import java.util.concurrent.DelayQueue;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：将订单推入延时队列
 */
public class PutOrder implements Runnable {
    private DelayQueue<ItemVo<Order>> queue;

    public PutOrder(DelayQueue<ItemVo<Order>> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        //5秒后超时
    	Order orderTb = new Order("TB123456789",300);
    	ItemVo<Order> itemTb = new ItemVo<Order>(5000,orderTb);
        queue.offer(itemTb);
        System.out.println("PutOrder5秒后超时："+orderTb.getOrderNo()+":"
        		+orderTb.getOrderMoney());
        //3秒后超时
        Order orderJd = new Order("JD987654321",289);
        ItemVo<Order> itemJd = new ItemVo<Order>(3000,orderJd);
        queue.offer(itemJd);
        System.out.println("PutOrder3秒后超时："+orderJd.getOrderNo()+":"+orderJd.getOrderMoney());

    }
}
