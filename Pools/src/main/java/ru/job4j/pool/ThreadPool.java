package ru.job4j.pool;

import java.util.LinkedList;
import java.util.List;
import ru.job4j.SimpleBlockingQueue;

/**
 * 
 * @author kirill
 *
 */
public class ThreadPool {
	private int size;
	private final List<Thread> threads = new LinkedList<>();
	private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

	/**
	 * Contructor.
	 * 
	 * @param size
	 */
	public ThreadPool() {
		this.size = Runtime.getRuntime().availableProcessors();
		for (int i = 0; i < this.size; i += 1) {
			Thread thread = new ThreadWorker(this.tasks);
			this.threads.add(thread);
			thread.start();
		}
	}

	/**
	 * Insert job into queue.
	 * 
	 * @param job
	 */
	public void work(final Runnable job) {
		this.tasks.offer(job);
	}

	/**
	 * Stopping all threads.
	 */
	public void shutdown() {
		for (Thread thread : this.threads) {
			thread.interrupt();
		}
		for (Thread thread : this.threads) {
			while (thread.isAlive()) {
				try {
					Thread.currentThread().sleep(50);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
	
	private class ThreadWorker extends Thread {
		private SimpleBlockingQueue<Runnable> tasks;

		public ThreadWorker(final SimpleBlockingQueue<Runnable> tasks) {
			this.tasks = tasks;
		}

		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					this.tasks.poll().run();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}
