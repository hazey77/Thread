package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LatchDriver {

	public static void main(String[] args) {
		
		int N = 10;
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch finishSignal = new CountDownLatch(N);
		ExecutorService executor = Executors.newFixedThreadPool(N);
		for (int i=0; i<N; i++)
			executor.submit(new Worker1(startSignal, finishSignal, i));
		executor.shutdown();
		System.out.println("All workers submitted.");
		startSignal.countDown();
		try {
			finishSignal.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All workers finished.");
	}

}

class Worker1 implements Runnable {
	private CountDownLatch startSignal;
	private CountDownLatch finishSignal;
	private int id;
	public Worker1(CountDownLatch startSignal, CountDownLatch finishSignal, int id) {
		super();
		this.startSignal = startSignal;
		this.finishSignal = finishSignal;
		this.id = id;
	}
	
	public void doWork() {
		System.out.println("Do work for id #"+id);
	}

	@Override
	public void run() {
		try {
			startSignal.await();
			doWork();
			finishSignal.countDown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
