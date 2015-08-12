package thread;


class Runner extends Thread {

	@Override
	public void run() {
		for (int i=0; i<10; i++) {
			System.out.println(Thread.currentThread() + " " + i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Runner2 implements Runnable {

	@Override
	public void run() {
		for (int i=0; i<5; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

public class DemoThread0 {
	


	public static void main(String[] args) {
		Runner run1 = new Runner();
		run1.start();
		Runner run2 = new Runner();
		run2.start();
		
		Runner2 run21 = new Runner2();
		Thread thread21 = new Thread(run21, "run21");
		thread21.start();
		
		Runner2 run22 = new Runner2();
		Thread thread22 = new Thread(run21, "run22");
		thread22.start();
	}


}
