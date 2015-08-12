package thread;

public class App1 {
	private int count = 0;
	
	public synchronized void increment() {count++;}
	
	public void test() {
		Thread th1 = new Thread(new Runnable(){
			public void run() {
				for (int i=0; i<10000; i++) {
					increment();
				}
			}
		});
		Thread th2 = new Thread(new Runnable(){
			public void run() {
				for (int i=0; i<10000; i++) {
					increment();
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
		System.out.println("count: "+ count);
	}

	public static void main(String[] args) {
		App1 app = new App1();
		app.test();
	}

}
