package com.xiangxue.ch2.wn;

/**
 * 
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：快递实体类
 */
public class Express {
    public final static String CITY = "ShangHai";
    private int km;/*快递运输里程数*/
    private String site;/*快递到达地点*/

    public Express() {
    }

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    /* 变化公里数，然后通知处于wait状态并需要处理公里数的线程进行业务处理*/
    public synchronized void changeKm(){
        this.km = 101;
        notifyAll();
    }

    /* 变化地点，然后通知处于wait状态并需要处理地点的线程进行业务处理*/
    public  synchronized  void changeSite(){
        this.site = "BeiJing";
        notifyAll();
    }

    public synchronized void waitKm(){
        while(this.km<=100){//公里数小于100不做处理
            try {
                wait();
                System.out.println("Check Km thread["+Thread.currentThread().getId()
                		+"] is be notified");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("the Km is "+this.km+",I will change db");
    }

    public synchronized void waitSite(){
        while(this.site.equals(CITY)){//快递到达目的地
            try {
                wait();
                System.out.println("Check Site thread["+Thread.currentThread().getId()
                		+"] is be notified");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("the site is "+this.site+",I will call user");
    }
}
