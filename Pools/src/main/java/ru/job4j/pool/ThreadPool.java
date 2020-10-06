package ru.job4j.pool;

import java.util.LinkedList;
import java.util.List;
import ru.job4j.SimpleBlockingQueue;

public class ThreadPool {
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
	
	private int size;
	private final List<Thread> threads = new LinkedList<>();
	private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

	/**
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
	 * 
	 * @param job
	 */
	public void work(final Runnable job) {
		this.tasks.offer(job);
	}

	/**
	 * 
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
	/**
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		ThreadPool threadPool = new ThreadPool();
		threadPool.work(() -> {
			System.out.println("Hello");
		});
		threadPool.shutdown();
	}
}
