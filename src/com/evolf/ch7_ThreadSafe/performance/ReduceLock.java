package com.evolf.ch7_ThreadSafe.performance;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *@author Mark老师   itheone itheone
 *
 *类说明：缩小锁的范围
 */
public class ReduceLock {
	
	private Map<String,String> matchMap = new HashMap<>();
	
	public synchronized boolean isMatch(String name,String regexp) {
		String key = "user."+name;
		String job = matchMap.get(key);
		if(job == null) {
			return false;
		}else {
			return Pattern.matches(regexp, job);//很耗费时间
		}
	}
	//缩小锁的范围
	public  boolean isMatchReduce(String name,String regexp) {
		String key = "user."+name;
		String job ;
		synchronized(this) {
			job = matchMap.get(key);
		}

		if(job == null) {
			return false;
		}else {
			return Pattern.matches(regexp, job);//耗费时间
		}
	}
	//锁粗化—扩大锁的范围
	public  boolean isMatchReduce2(String name,String regexp) {
		String key = "user."+name;
		String job ;
		synchronized(this) {
			job = matchMap.get(key);
			job = job + "s"; 			//耗费时间短
			job= matchMap.get(key);
		}

		if(job == null) {
			return false;
		}else {
			return Pattern.matches(regexp, job);//耗费时间长
		}
	}
	
}
