package examsSynch;

import java.util.HashSet;
import java.util.concurrent.Semaphore;

import synchronization.ProblemExecution;
import synchronization.TemplateThread;

public class Poker {
	
	public static Semaphore canSeat = new Semaphore(6);
	public static int players = 0;
	public static Semaphore lock = new Semaphore(1);
	public static boolean full = false;
	public static Semaphore canPlay = new Semaphore(0);
	
	
	public static void init() {
		
	}

  public static class Player extends TemplateThread {

    public Player(int numRuns) {
      super(numRuns);
    }

    @Override
    public void execute() throws InterruptedException {


         	canSeat.acquire();
         	state.playerSeat();
         	lock.acquire();
         	players++;
         	if (players==6){
         		state.dealCards();
         		canPlay.release(6);
         		
         	}
         	lock.release();
         	
         	canPlay.acquire();
         	state.play();
         	
         	lock.acquire();
         	players--;
         	if (players==0){
         		state.endRound();
         		canSeat.release(6);
         	}
         	lock.release();

        
         
         	
          

    

  }
    }

  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      run();
    }
  }

  static PokerState state = new PokerState();

  public static void run() {
    try {
      int numRuns = 1;
      int numIterations = 1200;

      HashSet<Thread> threads = new HashSet<Thread>();

      for (int i = 0; i < numIterations; i++) {
        Player c = new Player(numRuns);
        threads.add(c);
      }

      init();

      ProblemExecution.start(threads, state);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

}