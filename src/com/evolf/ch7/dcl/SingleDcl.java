package com.evolf.ch7.dcl;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 * 懒汉式-双重检查
 */
public class SingleDcl {
    private volatile static SingleDcl singleDcl;
    //private User user;  //复制可能耗费时间
    private SingleDcl(){
    }

    public static SingleDcl getInstance(){
    	if(singleDcl==null) {
    		synchronized (SingleDcl.class) {//类锁
				if(singleDcl==null) {
					singleDcl = new SingleDcl();//引用有了  但user不一定完成赋值
				}
			}
    	}
        return singleDcl;
    }
    //singleDcl.getUser.getId();//可能NullPointException
}
