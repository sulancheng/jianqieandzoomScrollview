package com.example.administrator.myjianqieqi.queredownload;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Administrator
 * on 2017/9/18.
 * 一个普通的队列
 */
public class MyQueue {
    private LinkedBlockingQueue list = new LinkedBlockingQueue();
//    private BlockingQueue blockingQueue = new BlockingQueue();

    public void clear()//销毁队列
    {
        list.clear();
    }

    public boolean QueueEmpty()//判断队列是否为空
    {
        return list.isEmpty();
    }

    public void addQueue(Object o)//进队
    {
        // offer方法向队列中添加元素，返回布尔值 使用add添加失败会报错。
        //queue.offer(o);
        list.offer(o);
    }

    public Object deQueue()//出队
    {
        // remove方法移除首个元素并返回,若队列为空,会抛出异常：NoSuchElementException，不推荐使用
        if (!list.isEmpty()) {
            return list.poll(); // 先进先出,弹出了a
        }
        return "队列为空";
    }

    public int QueueLength()//获取队列长度
    {
        return list.size();
    }

    public Object QueuePeek()//查看队首元素
    {
        // element方法返回队列的头元素，但不移除，若队列为空，会抛出异常：NoSuchElementException，不推荐使用
        // peek方法返回队列首个元素，但不移除，若队列为空，返回null
        return list.peek();
    }
    public static void main(String[] args)//测试队列
    {
        MyQueue queue = new MyQueue();
        System.out.println(queue.QueueEmpty());
        queue.addQueue("a");
        queue.addQueue("b");
        queue.addQueue("c");
        queue.addQueue("d");
        queue.addQueue("e");
        queue.addQueue("f");
        System.out.println(queue.QueueLength());
        System.out.println(queue.deQueue());
        System.out.println(queue.deQueue());
        System.out.println(queue.QueuePeek());
        System.out.println(queue.deQueue());
        queue.clear();
        queue.addQueue("s");
        queue.addQueue("t");
        queue.addQueue("r");
        System.out.println(queue.deQueue());
        System.out.println(queue.QueueLength());
        System.out.println(queue.QueuePeek());
        System.out.println(queue.deQueue());
    }
}
