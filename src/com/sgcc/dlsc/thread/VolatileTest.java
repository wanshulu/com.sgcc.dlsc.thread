package com.sgcc.dlsc.thread;

public class VolatileTest extends Thread{

	//volatile 修饰线程间可见变量
	private boolean isRunning = true;
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public void run() {
		System.out.println("进入run方法。。");
		while (isRunning == true) {
//			System.out.println("---");
		}
		System.out.println("线程终止。。");
	}
	
	public static void main(String[] args) throws InterruptedException {
		VolatileTest r = new VolatileTest();
		r.start();
		Thread.sleep(3000);
		r.setRunning(false);
		System.out.println("running被设置成false");
		Thread.sleep(1000);
		System.out.println(r.isRunning);
	}
	
}
