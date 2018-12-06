package com.evolf.ch7_ThreadSafe.dcl;


/**
 *@author Mark老师   itheone itheone
 *
 *类说明：
 */
public class InstanceLazy {
	
	private Integer value;
	private Integer val ;//可能很大，如巨型数组1000000;
	
    public InstanceLazy(Integer value) {
		super();
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
	
	private static class ValHolder {
		public static Integer vHolder = new Integer(1000000);
	}
	
	public Integer getVal() {
		return ValHolder.vHolder;
	}	

}
