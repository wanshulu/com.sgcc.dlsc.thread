package com.sgcc.dlsc.thread;

import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MyQueue {

	//1.��Ҫһ����װԪ�صļ���
	private LinkedList<Object> list = new LinkedList<Object>();
	//2.��Ҫһ��������
	private AtomicInteger count = new AtomicInteger(0);
	
	//��Ҫ�ƶ���������
	private final int minSize = 0;
	
	private final int maxSize;
	
	//4.���췽��
	public MyQueue(int maxSize) {
		this.maxSize = maxSize;
	}
	
	//5.��ʼ��һ���������ڼ���
	private final Object lock = new Object();
	
	//put(obj)  ��obj����ӵ�BlockingQueue����bq��û�пռ䣬����ô˷������̱߳���ϣ�ֱ��bq���пռ��ټ�����
	public void put(Object obj) {
		synchronized(lock) {
			while(this.maxSize == count.get()) {//�������������ֵ�ﵽ����޶�ֵ���������ȴ���
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//1.��������Ӷ���
			list.add(obj);
			//2.������+1
			count.incrementAndGet();
			//3.֪ͨ��һ���߳̿ɼ��������ѣ�
			lock.notify();
			System.out.println("����Ԫ�أ�"+obj);
		}
	}
	
	public Object take() {
		Object ret = null;
		synchronized(lock) {
			while(count.get() == this.minSize) {//�����ǰ����������=�趨��Сֵ�����̵߳ȴ�
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//1.ȡ����һ���Ž�ȥ��Ԫ��
			ret = list.removeFirst();
			//2.������-1
			count.decrementAndGet();
			//3.֪ͨ����һ���߳̿��Լ��������ѣ�
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
		
		//���Ԫ���߳�
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				mq.put("f");
				mq.put("g");
			}
		}, "t1");
		t1.start();
		
		//��ȡԪ���߳�
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				Object ret1 = mq.take();
				System.out.println("-ȡ��Ԫ�أ�"+ret1);
				Object ret2 = mq.take();
				System.out.println("-ȡ��Ԫ�أ�"+ret2);
			}
		}, "t2");
		
		try {
			TimeUnit.SECONDS.sleep(2);//�ȴ�2������ȡԪ���߳�
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.start();
		
	}
}
