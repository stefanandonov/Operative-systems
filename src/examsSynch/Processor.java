package examsSynch;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
 
public class Processor extends Thread {
 
  public static Random random = new Random();
  static List<EventGenerator> scheduled = new ArrayList<>();
  static Semaphore canGenerate=new Semaphore(5);
  static Semaphore canproces=new Semaphore(0);
 
 
  public static void main(String[] args) throws InterruptedException {
    // TODO: kreirajte Processor i startuvajte go negovoto pozadinsko izvrsuvanje
     Processor p = new Processor();
   
     
 
    for (int i = 0; i < 100; i++) {
      EventGenerator eventGenerator = new EventGenerator();
      register(eventGenerator);
      //TODO: startuvajte go eventGenerator-ot
      eventGenerator.start();
    }
    p.start();
 
    // TODO: Cekajte 20000ms za Processor-ot da zavrsi
    Thread.sleep(20000);
    // TODO: ispisete go statusot od izvrsuvanjeto
    if (p.isAlive()){
        p.interrupt();
        System.out.println("Interupt");
    }
    else
        System.out.println("done");
  }
 
  public static void register(EventGenerator generator) {
    scheduled.add(generator);
  }
 
  /**
   * Ne smee da bide izvrsuva paralelno so generate() metodot
   */
  public static synchronized void process() {
    System.out.println("processing event");
  }
 
 
  public void run() {
 
    while (!scheduled.isEmpty()) {
      // TODO: cekanje  na nov generiran event
        try {
            canproces.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      // TODO: povikajte go negoviot process() metod
      process();
    }
 
    System.out.println("Done scheduling!");
  }
}
 
 
class EventGenerator extends Thread {
 
  public Integer duration;
 
  public EventGenerator() throws InterruptedException {
    this.duration = Processor.random.nextInt(1000);
  }
 
 
  @Override
public void run() {
     
      try {
          Thread.sleep(this.duration);
         Processor.canGenerate.acquire();
         generate();
         Processor.canproces.release();
         Processor.canGenerate.release();
         Processor.scheduled.remove(this);
         
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
     
}
 
 
/**
   * Ne smee da bide povikan paralelno kaj poveke od 5 generatori
   */
  public static synchronized void  generate() {
    System.out.println("Generating event: ");
  }
}