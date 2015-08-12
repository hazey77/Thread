package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worker {
	private List<Integer> list1 = new ArrayList<Integer>();
	private List<Integer> list2 = new ArrayList<Integer>();
	private Random rand = new Random();
	
	private Object obj1 = new Object();
	private Object obj2 = new Object();

	public void add1() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized (obj1) {
			list1.add(rand.nextInt());
		}
	}
	
	public void add2() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized (obj2) {
			list2.add(rand.nextInt());
		}
	}
	
	public void process() {
		for (int i=0; i<1000; i++) {
			add1();
			add2();
		}
	}

	public void main() {
		System.out.println("Start process: ");
		long start = System.currentTimeMillis();
		Thread th1 = new Thread(new Runnable(){
			@Override
			public void run() {
				process();
			}
			
		});
		Thread th2 = new Thread(new Runnable(){
			@Override
			public void run() {
				process();
			}
			
		});
		
		th1.start();
		th2.start();
		try {
			th1.join();
			th1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		System.out.println("Time lapse: " + (end - start));
		System.out.println("list1: " + list1.size() + " list2: " + list2.size());
		
	}

}
