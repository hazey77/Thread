package thread;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Account {
	private int bal;

	public Account(int bal) {
		super();
		this.bal = bal;
	}

	public void deposit(int x) {
		this.bal += x;
	}

	public void withDraw(int x) {
		this.bal -= x;
	}

	public static void transfer(Account act1, Account act2, int amount) {
		act1.withDraw(amount);
		act2.deposit(amount);
	}
	
	public int balance() {
		return bal;
	}
}

class RunnerTest {
	private Account act1 = new Account(10000);
	private Account act2 = new Account(10000);
	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();

	public void aquireLock() {
		while (true) {
			boolean getLock1 = false;
			boolean getLock2 = false;
			try {
				getLock1 = lock1.tryLock();
				getLock2 = lock2.tryLock();

			} finally {
				if (getLock1 && getLock2) {
					return;
				}
				if (getLock1) {
					lock1.unlock();
				}
				if (getLock2) {
					lock2.unlock();
				}
			}
		}

	}

	public void firstThread() {
		Random rand = new Random();
		for (int i = 0; i < 1000; i++) {
			try {
				aquireLock();
				Account.transfer(act1, act2, rand.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}

		}
	}

	public void secondThread() {
		Random rand = new Random();
		for (int i = 0; i < 1000; i++) {
			try {
				aquireLock();
				Account.transfer(act1, act2, rand.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}

		}
	}
	
	public void total() {
		System.out.println("act1: " + act1.balance() + " act2: " + act2.balance());
		System.out.println("Total balance: " + (act1.balance()+act2.balance()));
	}

}

public class BankTest {

	public static void main(String[] args) {
		final RunnerTest runner = new RunnerTest();
		Thread th1 = new Thread(new Runnable() {
			@Override
			public void run() {
				runner.firstThread();
			}
		});
		Thread th2 = new Thread(new Runnable() {
			@Override
			public void run() {
				runner.secondThread();
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
		
		runner.total();
	}

}
