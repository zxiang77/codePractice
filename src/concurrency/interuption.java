package concurrency;

public class interuption {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread t = new Thread(new Runnable(){
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i = 0; i < 1E6; i++) {
					if (Thread.currentThread().isInterrupted()) {
						System.out.println("Interrupted!!!");
						break;
					}
//					try{
//						// do something	
//						int j = 0;
//					} catch(InterruptedException e) {
//						
//					}				
				}
			}
			
		});
		
		t.start();
		Thread.sleep(10);
		t.interrupt();
		t.join();

		
		
		
		
	}

}
