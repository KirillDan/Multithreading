package ru.job4j.buffer;

import ru.job4j.SimpleBlockingQueue;

/**
 * This class demonstrate working with SimpleBlockingQueue.
 * @author kirill
 *
 */
public class ParallelSearch {
	protected ParallelSearch() {
	}

	/**
	 * This is main method.
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
		final Thread consumer = new Thread(() -> {
			while (true) {
				try {
					System.out.println(queue.poll());
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		});
		consumer.setDaemon(true);
		consumer.start();
		final Thread producer = new Thread(() -> {
			for (int index = 0; index != 3; index++) {
				queue.offer(index);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		producer.start();
	}
}
