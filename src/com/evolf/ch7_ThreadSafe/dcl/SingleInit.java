package com.evolf.ch7_ThreadSafe.dcl;

/**
 * @author Mark老师   itheone itheone
 * 懒汉式-类初始化模式
 */
public class SingleInit {
    private SingleInit(){}

    //定义一个私有类，来持有当前类的实例(在JVM中，对类的加载和类初始化，由虚拟机保证线程安全)
    //私有类如果未被使用到，则不会被初始化
    private static class InstanceHolder{
        public static SingleInit instance = new SingleInit();
    }

    public static SingleInit getInstance(){
        return InstanceHolder.instance;
    }

}
