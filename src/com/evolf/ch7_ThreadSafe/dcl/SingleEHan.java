package com.evolf.ch7_ThreadSafe.dcl;

/**
 * @author Mark老师   itheone itheone
 * 饿汉式
 */
public class SingleEHan {
    public static SingleEHan singleEHan = new SingleEHan();
    private SingleEHan(){}

}
