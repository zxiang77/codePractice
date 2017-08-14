package concurrency;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPool {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService es = Executors.newCachedThreadPool();
		
//		es.submit(new Runnable(){
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
		
		Future<Integer> f = es.submit(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				Random rand = new Random();
				int wait = rand.nextInt(100);
				
				Thread.sleep(wait);
				
				return wait;
			}
			
		});
		
		es.shutdown();
		
		try {
			System.out.println(f.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
