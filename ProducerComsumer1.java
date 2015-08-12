package thread;

import java.util.LinkedList;

public class ProducerComsumer1 {
	private LinkedList<Integer> list = new LinkedList<Integer>();
	private Object lock = new Object();
	private int val;
	final private int LIMIT=5;
	
	public void produce() throws InterruptedException {
		while(true) {
			synchronized (lock) {
				//Thread.sleep(1);
				while(list.size()==LIMIT) {
					lock.wait();
				}
				list.add(val++);
				lock.notify();
			}
		}
	}
	
	public void comsume() throws InterruptedException {
		while(true) {
			synchronized (lock) {
				while(list.size()==0) {
					lock.wait();
				}
				System.out.println("Size:" + list.size() + " Remove " + list.removeFirst());
				lock.notify();
			}
			Thread.sleep(1000);

		}
	}

	public static void main(String[] args) {
		final ProducerComsumer1 proc = new ProducerComsumer1();
		Thread th1 = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					proc.produce();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Thread th2 = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					proc.comsume();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		th1.start();
		th2.start();
		try {
			th1.join();
			th2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
