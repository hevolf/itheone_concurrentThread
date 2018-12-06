package com.evolf.ch3_CAS;

import java.util.concurrent.atomic.AtomicIntegerArray;


/**
 *@author Mark老师   itheone itheone
 *
 *类说明：
 */
public class AtomicArray {
    static int[] value = new int[] { 1, 2 };
    
    static AtomicIntegerArray ai = new AtomicIntegerArray(value);//对原有 value 无影响
    
    public static void main(String[] args) {
    	ai.getAndSet(0, 3);
    	System.out.println(ai.get(0));
    	System.out.println(value[0]);

    }
}
