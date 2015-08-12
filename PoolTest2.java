package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

class Connection {
	private static Connection instance = new Connection();
	private int connections = 0;
	Semaphore sem = new Semaphore(3, true);

	private Connection() {
	}

	public static Connection getInstance() {
		return instance;
	}
	
	public void connect() {
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			doConnect();
		}
		finally {
			sem.release();
		}
	}

	public void doConnect() {
		
		synchronized (this) {
			connections++;
			System.out.println("Total connections: " + connections);
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized (this) {
			connections--;
		}
	}

}

public class PoolTest2 {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i=0; i<10; i++) {
			executor.execute(new Runnable() {
				public void run() {
					Connection.getInstance().connect();
				}
			});
		}
		
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
