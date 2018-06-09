package com.sgcc.dlsc.thread;

public class ConnThreadLocal {
	
	public static ThreadLocal<String> th = new ThreadLocal<String>();
	
	public void setTh(String value) {
		th.set(value);
	}
	
	public void getTh() {
		System.out.println(Thread.currentThread().getName()+":"+th.get());
	}

	public static void main(String[] args) {
		final ConnThreadLocal ctl  = new ConnThreadLocal();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				ctl.setTh("zhangsan");
				ctl.getTh();
			}
		}, "t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					ctl.getTh();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, "t2");
		
		t1.start();
		t2.start();

	}

}
