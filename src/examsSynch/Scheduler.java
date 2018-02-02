package examsSynch;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
 
public class Scheduler extends Thread{
  public static Random random = new Random();
  static List<Process> scheduled = new ArrayList<>();
  public static Semaphore s = new Semaphore(1);
  public static List<Integer> test1 = new ArrayList<>();
  public static List<Integer> test2 = new ArrayList<>();
 
  public static void main(String[] args) throws InterruptedException {
    // TODO: kreirajte 100 Process nitki i registrirajte gi
	  for (int i=0;i<100;i++){
		  Process p = new Process();
		  Scheduler.register(p);
		  test2.add(p.duration);
	  }
 
    // TODO: kreirajte Scheduler i startuvajte go negovoto pozadinsko izvrsuvanje
	  Scheduler s = new Scheduler();
	  s.start();
 
  }
 
  public static void register(Process process) {
    scheduled.add(process);
  }
 
  public Process next() {
    if (!scheduled.isEmpty()) {
      return scheduled.remove(0);
    }
    return null;
  }
 
  public void run() {
    try {
          while (!scheduled.isEmpty()) {
            Thread.sleep(100);
            System.out.print(".");
 
            // TODO: zemete go naredniot proces
            s.acquire();
            Process process = this.next();
 
            // TODO: povikajte go negoviot execute() metod
            process.execute();
            test1.add(process.duration); //za testiranje
 
            // TODO: Cekajte 700 ms za processot da zavrsi
            process.join(700);
 
            // TODO: ispisete go statusot od izvrsuvanjeto
            if (process.isAlive()){
            	process.interrupt();
            	System.out.println("Terminated processing");
            }
            else {
            	System.out.println("Finished processing");
            }
            
            s.release();
 
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("Done scheduling!");
        int correct = 0;
        int wrong = 0;
        for (int i=0;i<test1.size();i++){
        	if (test1.get(i)==test2.get(i))
        		++correct;
        	else
        		++wrong;
        }
        System.out.println("Number od correct scheduled processes: " + correct + "out of "+test1.size());
      }
  }
 

 
 
class Process extends Thread{
 
  public Integer duration;
 
  public Process() throws InterruptedException {
    this.duration = Scheduler.random.nextInt(1000);
  }
  
  public void run() {
	  
	  try {
		Thread.sleep(this.duration);
	} catch (InterruptedException e) {
		//e.printStackTrace();
	}
	  
	  
  }
 
 
  public void execute() {
    System.out.println("Executing[" + this + "]: " + duration);
    // TODO: startuvajte go pozadinskoto izvrsuvanje
    	this.start();
    
  }
}