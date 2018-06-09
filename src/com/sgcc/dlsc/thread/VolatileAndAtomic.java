package com.sgcc.dlsc.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileAndAtomic extends Thread {

//	private static volatile int count;
	private static AtomicInteger count = new AtomicInteger(0);
	
	private  void addCount() {
		for (int i = 0; i < 1000; i++) {
//			count ++;
			count.incrementAndGet();
		}
		System.out.println(count);
	}
	
	public void run() {
		addCount();
	}
	
	public static void main(String[] args) {
		VolatileAndAtomic[] vaa = new VolatileAndAtomic[10];
		for (int i = 0; i < vaa.length; i++) {
			vaa[i] = new VolatileAndAtomic();
		}
		
		for (int i = 0; i < vaa.length; i++) {
			vaa[i].start();
		}
	}
}
