package ru.job4j;

import java.util.LinkedList;
import java.util.Queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * 
 * @author kirill
 *
 * @param <T>
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
	@GuardedBy("this")
	private Queue<T> queue = new LinkedList<T>();
	private final Object monitor = this;
	private final int total;

	/**
	 * 
	 * @param total
	 */
	public SimpleBlockingQueue(final int total) {
		this.total = total;
	}

	/**
	 * 
	 */
	public SimpleBlockingQueue() {
		this.total = Integer.MAX_VALUE;
	}

	/**
	 * 
	 * @param value
	 */
	public void offer(final T value) {
		synchronized (monitor) {
			while (this.queue.size() >= total) {
				try {
					monitor.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			monitor.notifyAll();
			this.queue.offer(value);
		}
	}

	/**
	 * 
	 * @return T
	 * @throws InterruptedException
	 */
	public T poll() throws InterruptedException {
		synchronized (monitor) {
			while (this.queue.isEmpty()) {
				monitor.wait();
			}
			monitor.notifyAll();
			return this.queue.poll();
		}
	}
}
