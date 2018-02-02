package examsSynch;

import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import synchronization.ProblemExecution;
import synchronization.TemplateThread;

public class CalciumNitride {
	
	public static int caNum = 0;
	public static Semaphore ca = new Semaphore(3);
	public static Semaphore n = new Semaphore(2);
	
	public static Semaphore lock = new Semaphore(1);
	
	public static Semaphore caHere = new Semaphore(0);
	public static Semaphore nHere = new Semaphore(0);
	
	public static Semaphore ready = new Semaphore(0);
	public static Semaphore bondingDone = new Semaphore(0);
	public static Semaphore canLeave = new Semaphore(0);

    public static void init() {
    }

    public static class Calcium extends TemplateThread {

        public Calcium(int numRuns) {
            super(numRuns);
        }

        @Override
        public void execute() throws InterruptedException {
        	
        	ca.acquire();
        	lock.acquire();
        	caNum++;
        	if (caNum==3) {
        		caNum=0;
        		lock.release();
        		nHere.acquire(2);
        		ready.release(4);
        		state.bond();
        		//bondingDone.release();
        		bondingDone.acquire(4);
        		canLeave.release(4);
        		state.validate();
        		ca.release(3);        		
        	}
        	else {
        		lock.release();
        		ready.acquire();
        		state.bond();
        		bondingDone.release();
        		canLeave.acquire();
        		
        		
        	}
            

           // state.validate();
        }
    }

    public static class Nitrogen extends TemplateThread {

        public Nitrogen(int numRuns) {
            super(numRuns);
        }

        @Override
        public void execute() throws InterruptedException {
            n.acquire();
            nHere.release();
            ready.acquire();
        	state.bond();
        	bondingDone.release();
        	canLeave.acquire();
        	n.release();
        	
            
            
        }
    }
    static CalciumNitrideState state = new CalciumNitrideState();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            run();
        }
    }

    public static void run() {
        try {
            Scanner s = new Scanner(System.in);
            int numRuns = 1;
            int numIterations = 100;
            s.close();

            HashSet<Thread> threads = new HashSet<Thread>();

            for (int i = 0; i < numIterations; i++) {
                Nitrogen n = new Nitrogen(numRuns);
                threads.add(n);
                Calcium ca = new Calcium(numRuns);
                threads.add(ca);
                ca = new Calcium(numRuns);
                threads.add(ca);
                n = new Nitrogen(numRuns);
                threads.add(n);
                ca = new Calcium(numRuns);
                threads.add(ca);
            }

            init();

            ProblemExecution.start(threads, state);
            System.out.println(new Date().getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
