package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentranceTest {
	private int val;
	private Lock lock = new ReentrantLock();

	private void increment() {
		for (int i = 0; i < 1000; i++) {
			val++;
		}
	};

	public void first() throws InterruptedException {
		Thread.sleep(500);
		lock.lock();
		try {
			System.out.println("First");
			increment();
		} finally {
			lock.unlock();
		}
	}

	public void second() throws InterruptedException {
		lock.lock();
		try {
			System.out.println("Second");
			increment();
		} finally {
			lock.unlock();
		}
	}

	public void output() {
		System.out.println(val);
	}

	public static void main(String[] args) {
		final ReentranceTest test = new ReentranceTest();
		Thread th1 = new Thread(new Runnable() {
			public void run() {
				try {
					test.first();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Thread th2 = new Thread(new Runnable() {
			public void run() {
				try {
					test.second();
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

		test.output();
	}

}
