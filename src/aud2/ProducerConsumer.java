package aud2;
//package mk.ukim.finki.os.synchronization.problems;

import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import synchronization.AbstractState;
import synchronization.BoundCounterWithRaceConditionCheck;
import synchronization.PointsException;
import synchronization.ProblemExecution;
import synchronization.TemplateThread;

/**
 * 
 * @author ristes
 */
public class ProducerConsumer {

	public static int NUM_RUNS = 10;

	// TODO: Define all semaphores and other variables here
	static Semaphore buffer;
	static Semaphore[] items;
	static final Object bufferAccess = new Object();

	/**
	 * A method for initializing the values of the semaphores
	 * and the other variables necessary.
	 * 
	 * TODO: Implement this method
	 * 
	 */
	public static void init() {
		int numConsumers = state.getBufferCapacity();
		buffer = new Semaphore(1);
		items = new Semaphore[numConsumers];
		for (int i = 0; i < numConsumers; i++) {
			items[i] = new Semaphore(0);
		}
	}

	static class Producer extends TemplateThread {

		public Producer(int numRuns) {
			super(numRuns);
		}

		@Override
		public void execute() throws InterruptedException {
			buffer.acquire();
			state.fillBuffer();
			 //signal to the consumers that the buffer is filled
			for (int i = 0; i < items.length; i++) {
				items[i].release();
			}
		}
	}

	static class Consumer extends TemplateThread {
		private int cId;

		public Consumer(int numRuns, int id) {
			super(numRuns);
			cId = id;
		}

		@Override
		public void execute() throws InterruptedException {
			items[cId].acquire();
			state.getItem(cId);
			synchronized (bufferAccess) {
				state.decrementNumberOfItemsLeft();
				if (state.isBufferEmpty()) {
					// signal the producer to fill the buffer
					buffer.release();
				}
			}
		}
	}

	// <editor-fold defaultstate="collapsed" desc="This is the template code" >
	static State state;

	static class State extends AbstractState {

		private static final String _10_DVAJCA_ISTOVREMENO_PROVERUVAAT = "Two threads are checking the buffer at the same time. Only one is allowed to do that at a given time.";
		private static final String _10_KONZUMIRANJETO_NE_E_PARALELIZIRANO = "The consuming is not parallel..";
		private int bufferCapacity = 15;

		private BoundCounterWithRaceConditionCheck[] items;
		private BoundCounterWithRaceConditionCheck counter = new BoundCounterWithRaceConditionCheck(
				0);
		private BoundCounterWithRaceConditionCheck raceConditionTester = new BoundCounterWithRaceConditionCheck(
				0);
		private BoundCounterWithRaceConditionCheck bufferFillCheck = new BoundCounterWithRaceConditionCheck(
				0, 1, 10, "", null, 0, null);

		public int getBufferCapacity() {
			return bufferCapacity;
		}

		private int itemsLeft = 0;

		public State(int capacity) {
			bufferCapacity = capacity;
			items = new BoundCounterWithRaceConditionCheck[bufferCapacity];
			for (int i = 0; i < bufferCapacity; i++) {
				items[i] = new BoundCounterWithRaceConditionCheck(0, null, 0,
						null, 0, 10, "You cannot get an item from an empty buffer.");
			}
		}

		public boolean isBufferEmpty() throws RuntimeException {
			log(raceConditionTester.incrementWithMax(), "Checking buffer state");
			boolean empty = (itemsLeft == 0);
			log(raceConditionTester.decrementWithMin(), null);
			return empty;
		}

		public void getItem(int index) {
			counter.incrementWithMax(false);
			log(items[index].decrementWithMin(), "Getting item");
			counter.decrementWithMin(false);
		}

		public void decrementNumberOfItemsLeft() {
			counter.incrementWithMax(false);
			synchronized (this) {
				itemsLeft--;
			}
			counter.decrementWithMin(false);
		}

		public void fillBuffer() {
			log(bufferFillCheck.incrementWithMax(), "Filling buffer");
			if (isBufferEmpty()) {
				for (int i = 0; i < bufferCapacity; i++) {
					items[i].incrementWithMax();

				}
			} else {
				logException(new PointsException(10, "Filling non-empty buffer"));
			}
			synchronized (this) {
				itemsLeft = bufferCapacity;
			}
			log(bufferFillCheck.decrementWithMin(), null);
		}

		public void finalize() {
			if (counter.getMax() == 1) {
				logException(new PointsException(10,
						_10_KONZUMIRANJETO_NE_E_PARALELIZIRANO));
			}
		}
	}

	public static void main(String[] args) {
		try {
			Scanner s = new Scanner(System.in);
			int brKonzumeri = s.nextInt();
			int numIterations = s.nextInt();
			s.close();

			HashSet<Thread> threads = new HashSet<Thread>();

			for (int i = 0; i < brKonzumeri; i++) {
				Consumer c = new Consumer(numIterations, i);
				threads.add(c);
			}
			Producer p = new Producer(numIterations);
			threads.add(p);

			state = new State(brKonzumeri);

			init();

			ProblemExecution.start(threads, state);

			 //state.printLog();
			 
			 state.printStatus();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// </editor-fold>
}

