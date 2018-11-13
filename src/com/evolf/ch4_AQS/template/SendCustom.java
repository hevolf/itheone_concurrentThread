package com.evolf.ch4_AQS.template;

import java.util.Date;

/**
 * @author evolf
 *模板方法的父类
 */
public abstract class SendCustom {
	
	public abstract void to();
	public abstract void from();
	public abstract void content();
	public void date() {
		System.out.println(new Date());
	}
	public abstract void send();
	
	//框架方法-模板方法-依次调用模板
	public void sendMessage() {
		to();
		from();
		content();
		date();
		send();
	}

}
