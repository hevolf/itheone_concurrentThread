package com.evolf.ch3;

import java.util.concurrent.atomic.AtomicReference;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：演示引用类型的原子操作类
 */
public class UseAtomicReference {
	
	static AtomicReference<UserInfo> userRef = new AtomicReference<>();
	
    public static void main(String[] args) {
        UserInfo user = new UserInfo("Mark", 15);//要修改的实体的实例
        userRef.set(user);
        
        UserInfo updateUser = new UserInfo("Bill", 17);//要变化的新实例
        //user期望更改的值  updateUser更改后的新值  即该内存地址V 上的值如果是user 则更改（原子操作）
        //若内存地址v的值 和user地址的值相等 则对内存地址v的值进行更改，user不影响（只用来做比较判断）
        userRef.compareAndSet(user, updateUser);
        System.out.println(userRef.get().getName());
        System.out.println(userRef.get().getAge());
        System.out.println(user.getName());
        System.out.println(user.getAge());        
    }
    
    //定义一个实体类
    static class UserInfo {
        private String name;
        private int age;
        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }
        public String getName() {
            return name;
        }
        public int getAge() {
            return age;
        }
    }

}
