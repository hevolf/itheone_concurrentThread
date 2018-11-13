package com.xiangxue.ch3.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：演示CyclicExchange用法
 */
public class UseExchange {
    private static final Exchanger<Set<String>> exchange = new Exchanger<Set<String>>();

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
            	Set<String> setA = new HashSet<String>();//存放数据的容器
                try {
                	/*添加数据
                	 * set.add(.....)
                	 * */
                	setA = exchange.exchange(setA);//交换set
                	/*处理交换后的数据*/
                } catch (InterruptedException e) {
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
            	Set<String> setB = new HashSet<String>();//存放数据的容器
                try {
                	/*添加数据
                	 * set.add(.....)
                	 * set.add(.....)
                	 * */
                	setB = exchange.exchange(setB);//交换set
                	/*处理交换后的数据*/
                } catch (InterruptedException e) {
                }
            }
        }).start();

    }
}
