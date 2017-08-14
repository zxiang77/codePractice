package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class reentrantLock {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	}

}

class Runner2 {
	Lock lock1 = new ReentrantLock();
	Lock lock2 = new ReentrantLock();
	
	Account acc1 = new Account();
	Account acc2 = new Account();
	
	// method 2
	public void acquireLock(Lock lock1, Lock lock2) throws InterruptedException {
		boolean getFirst  = false;
		boolean getSecond = false;
		
		while(true) {
			try {
				getFirst = lock1.tryLock();
				getSecond = lock2.tryLock();
			} finally {
				if (getFirst && getSecond) return;
				
				if (getFirst) lock1.unlock();
				if (getSecond) lock2.unlock();
			}
			Thread.sleep(1);
		}
	}
	// or always acquire lock in order
	public void transfer(Account from, Account to) {
		
	}
	
}

class Account {
	public int balance;
}