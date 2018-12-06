package com.evolf.ch4_AQS.ReentrantReadWriteLock;

/**
 *@author Mark老师   itheone itheone
 *
 *类说明：商品的服务的接口
 */
public interface GoodsService {

	public GoodsInfo getNum();//获得商品的信息
	public void setNum(int number);//设置商品的数量
}
