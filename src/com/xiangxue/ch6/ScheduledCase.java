package com.xiangxue.ch6;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.xiangxue.tools.SleepTools;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：演示ScheduledThreadPoolExecutor的用法
 */
public class ScheduledCase {
    public static void main(String[] args) {
    	
    	ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(1);
    	
//        //延时Runnable任务（仅执行一次）
//        schedule.schedule(new Runnable() {
//            public void run() {
//                System.out.println("-----The task only run once!----");
//            }
//        }, 3000, TimeUnit.MILLISECONDS);
//        
//        //固定延时间隔执行的任务
//        schedule.scheduleWithFixedDelay(new Runnable() {
//            public void run() {
//                System.out.println("******FixedDelay:start,"
//                        + ScheduleWorker.formater.format(new Date()));
//                SleepTools.second(2);
//                System.out.println("******FixedDelay:end,"
//                        + ScheduleWorker.formater.format(new Date()));
//            }
//        }, 1000, 3000, TimeUnit.MILLISECONDS);
    	
        //固定时间间隔执行的任务,第一次任务在1000ms后执行，第二次任务在1000+3000 ms后执行，第三次在
        //1000+3000*2 ms后执行
        schedule.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.Normal),
                1000, 3000, TimeUnit.MILLISECONDS);

        // 固定时间间隔执行的任务,开始执行后就触发异常,next周期将不会运行
        schedule.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.HasException),
                1000, 3000, TimeUnit.MILLISECONDS);

        // 固定时间间隔执行的任务,虽然抛出了异常,但被捕捉了,next周期继续运行
        schedule.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.ProcessException),
                1000, 3000, TimeUnit.MILLISECONDS);




    }
}
