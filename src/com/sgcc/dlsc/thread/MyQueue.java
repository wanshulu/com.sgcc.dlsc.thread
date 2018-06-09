package com.sgcc.dlsc.thread;

import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MyQueue {

	//1.需要一个承装元素的集合
	private LinkedList<Object> list = new LinkedList<Object>();
	//2.需要一个计数器
	private AtomicInteger count = new AtomicInteger(0);
	
	//需要制定上限下限
	private final int minSize = 0;
	
	private final int maxSize;
	
	//4.构造方法
	public MyQueue(int maxSize) {
		this.maxSize = maxSize;
	}
	
	//5.初始化一个对象，用于加锁
	private final Object lock = new Object();
	
	//put(obj)  把obj对象加到BlockingQueue里，如果bq里没有空间，则调用此方法的线程被阻断，直到bq里有空间再继续。
	public void put(Object obj) {
		synchronized(lock) {
			while(this.maxSize == count.get()) {//如果计数器的数值达到最大限定值，则锁定等待。
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//1.集合中添加对象
			list.add(obj);
			//2.计数器+1
			count.incrementAndGet();
			//3.通知另一个线程可继续（唤醒）
			lock.notify();
			System.out.println("加入元素："+obj);
		}
	}
	
	public Object take() {
		Object ret = null;
		synchronized(lock) {
			while(count.get() == this.minSize) {//如果当前计数器个数=设定最小值，则线程等待
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//1.取出第一个放进去的元素
			ret = list.removeFirst();
			//2.计数器-1
			count.decrementAndGet();
			//3.通知另外一个线程可以继续（唤醒）
			lock.notify();
		}
		return ret;
	}
	
	public int getCount() {
		return this.count.get();
	}
	
	public static void main(String[] args) {
		
		MyQueue mq = new MyQueue(5);
		mq.put("a");
		mq.put("b");
		mq.put("c");
		mq.put("d");
		mq.put("e");
		
		//添加元素线程
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				mq.put("f");
				mq.put("g");
			}
		}, "t1");
		t1.start();
		
		//获取元素线程
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				Object ret1 = mq.take();
				System.out.println("-取出元素："+ret1);
				Object ret2 = mq.take();
				System.out.println("-取出元素："+ret2);
			}
		}, "t2");
		
		try {
			TimeUnit.SECONDS.sleep(2);//等待2秒启动取元素线程
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.start();
		
	}
}
