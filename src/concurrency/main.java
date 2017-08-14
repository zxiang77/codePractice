package concurrency;

public class main {
	int counter = 0;
	public static void main(String[] args) {
		main m = new main();
		m.doWork();
	}
	
	public void doWork() {
		Thread runner1 = new Thread(new Runnable(){
			public void run() {
				// TODO Auto-generated method stub
				for(int i = 0; i < 10000; i++) counter++;
			}
		});
		
		Thread runner2 = new Thread(new Runnable(){
			public void run() {
				for(int i = 0; i < 10000; i++) counter++;
			}
		});
		
		runner1.start();
		runner2.start();
		
		try {
			runner1.join();
			runner2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Counter: " + counter);
	}
	
}

class Runner extends Thread {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 0; i < 10; i++) {
			System.out.println(i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}