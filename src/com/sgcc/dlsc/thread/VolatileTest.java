package com.sgcc.dlsc.thread;

public class VolatileTest extends Thread{

	//volatile �����̼߳�ɼ�����
	private boolean isRunning = true;
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public void run() {
		System.out.println("����run��������");
		while (isRunning == true) {
//			System.out.println("---");
		}
		System.out.println("�߳���ֹ����");
	}
	
	public static void main(String[] args) throws InterruptedException {
		VolatileTest r = new VolatileTest();
		r.start();
		Thread.sleep(3000);
		r.setRunning(false);
		System.out.println("running�����ó�false");
		Thread.sleep(1000);
		System.out.println(r.isRunning);
	}
	
}
