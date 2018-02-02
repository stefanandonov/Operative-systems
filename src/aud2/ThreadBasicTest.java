package aud2;
import java.lang.Thread;
import java.util.stream.IntStream;

class ThreadA1 extends Thread {
	@Override
	public void run() {
		IntStream.range(1, 20).forEach(x -> System.out.println("A: "+x));
		System.out.println("Thread A is done");
	}
}

class ThreadB1 extends Thread{
	@Override
	public void run() {
		for (int i=-1;i>=-20;i--){
			System.out.println("\t\tB: " + i);
		}
		System.out.println("Thread B is done");
	}
}

public class ThreadBasicTest {
	
	public static void main(String [] args){
		
		Thread ta = new ThreadA1();
		Thread tb = new ThreadB1();
		ta.start();
		tb.start();
		System.out.println("Main done");
		
	}

}
