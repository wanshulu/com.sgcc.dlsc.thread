package com.sgcc.dlsc.thread;

/**
 * �������ڳ������ʱ��Ӧ�ñ������˫���໥���жԷ����������
 * @author Administrator
 *
 */
public class Deadlock extends Thread {

	private String tag;
	private static Object lock1 = new Object();
	private static Object lock2 = new Object();
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public void run() {
		if ("a".equals(tag)) {
			synchronized(lock1) {
				try {
					System.out.println("��ǰ�̣߳�"+Thread.currentThread().getName()+" ����lock1ִ��");
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized(lock2) {
					System.out.println("��ǰ�̣߳�"+Thread.currentThread().getName()+" ����lock2ִ��");
				}
			}
		}
		if ("b".equals(tag)) {
			synchronized(lock2) {
				try {
					System.out.println("��ǰ�̣߳�"+Thread.currentThread().getName()+" ����lock2ִ��");
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized(lock1) {
					System.out.println("��ǰ�̣߳�"+Thread.currentThread().getName()+" ����lock1ִ��");
				}
			}
		}
	}

	public static void main(String[] args) {
		Deadlock d1 = new Deadlock();
		d1.setTag("a");
		Deadlock d2 = new Deadlock();
		d2.setTag("b");
		
		Thread t1 = new Thread(d1, "t1");
		Thread t2 = new Thread(d2, "t2");
		
		t1.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.start();
	}
}
