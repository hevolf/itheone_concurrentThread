package com.evolf.ch3_CAS;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *@author Mark老师   itheone itheone
 *
 *类说明：
 */
public class UseAtomicInt {
	static int value = 10;
	
	static AtomicInteger ai = new AtomicInteger(value);
	
    public static void main(String[] args) {
    	System.out.println(ai.getAndIncrement());//10--->11
    	System.out.println(ai.incrementAndGet());//11--->12--->out
    	System.out.println(ai.get());
		System.out.println(value);
	}
}
