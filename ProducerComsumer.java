package thread;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerComsumer {
	private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

	public static void main(String[] args) throws InterruptedException {
		Thread th1 = new Thread(new Producer());
		Thread th2 = new Thread(new Comsumer());
		th1.start();
		th2.start();
		th1.join();
		th2.join();
	}

	public static class Producer implements Runnable {

		@Override
		public void run() {
			Random rand = new Random();
			while (true) {
				try {
					Thread.sleep(20);
					queue.put(rand.nextInt(100));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static class Comsumer implements Runnable {

		@Override
		public void run() {
			Random rand = new Random();
			while (true) {
				try {
					Thread.sleep(100);
					if (rand.nextInt(100) < 50) {
						int val = queue.take();
						System.out.println("Value taken out: " + val
								+ " queue size is: " + queue.size());
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
