package com.evolf.ch7;

import java.util.concurrent.DelayQueue;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：检测延时队列，取出到期的订单
 */
public class FetchOrder implements Runnable {
    private DelayQueue<ItemVo<Order>> queue;

    public FetchOrder(DelayQueue<ItemVo<Order>> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true){
            try {
            	ItemVo<Order> item = queue.take();
            	Order order = (Order)item.getData();
                System.out.println("GetFromQueue："
                        +" data = "+order.getOrderNo()+"-"+order.getOrderMoney());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
