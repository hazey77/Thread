package thread;

import java.util.Scanner;

class Processor extends Thread {
	private volatile boolean running = true;

	@Override
	public void run() {
		while(running) {
			System.out.println("Running...");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void shutdown() {
		running = false;
	}
	
}

public class App {

	public static void main(String[] args) {
		/*
		Processor proc1 = new Processor();
		proc1.start();
		
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		System.out.println("Stop");
		proc1.shutdown();
		*/
		
		new Worker().main();
	}

}
