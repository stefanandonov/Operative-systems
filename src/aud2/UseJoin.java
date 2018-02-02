package aud2;


public class UseJoin {

	public static void main(String[] args) {
		Count c = new Count();
		c.start();
		try {
			c.join(); //what would happen without this line
			System.out.println("Result = " + c.getResult());
		} catch (InterruptedException e) {}

	}

}

class Count extends Thread {
	private long result;

	public void run() {
		result = count();
	}

	public long getResult() {
		return result;
	}

	public long count() {
		long r = 0;
		for (r = 0; r < 1000000; r++)
			;
		return r;
	}

}

