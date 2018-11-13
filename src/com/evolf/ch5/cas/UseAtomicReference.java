package com.evolf.ch5.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 安生老师 QQ：669100976  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：演示引用类型的原子操作类
 */
public class UseAtomicReference {
    public static AtomicReference<UserInfo> atomicUserRef = new
            AtomicReference<UserInfo>();//原子引用类型的实例
    public static void main(String[] args) {
        UserInfo user = new UserInfo("Mark", 15);//要修改的实体的实例
        atomicUserRef.set(user);//用原子引用类型包装
        UserInfo updateUser = new UserInfo("Bill", 17);//要变化成为的新实例
        atomicUserRef.compareAndSet(user, updateUser);
        System.out.println(atomicUserRef.get().getName());
        System.out.println(atomicUserRef.get().getAge());
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
