package com.itheone.ch1.volatile_unsafe;

/**
 * 类说明：演示 volatile
 */
public class NotSafe {
    //类变量
    private volatile long count =0;
    //getter
    public long getCount() {
        return count;
    }
    //setter
    public void setCount(long count) {
        this.count = count;
    }

    //count进行累加
    public void incCount(){
        count++;
    }

    //线程
    private static class Count extends Thread{

        private NotSafe simplOper;

        public Count(NotSafe simplOper) {
            this.simplOper = simplOper;
        }

        @Override
        public void run() {
            for(int i=0;i<10000;i++){
                simplOper.incCount();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //同一个对象 simplOper
        NotSafe simplOper = new NotSafe();
        //启动两个线程，每个线程操作simplOper对象，循环累加10000次
        Count count1 = new Count(simplOper);
        Count count2 = new Count(simplOper);
        count1.start();
        count2.start();
        Thread.sleep(50);
        System.out.println(simplOper.count);
    }
}
