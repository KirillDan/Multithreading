package ru.job4j;

/**
 * This class realize count waiting Barrier.
 * 
 * @author kirill
 *
 */
public class CountBarrier {
	private final Object monitor = this;

	private final int total;

	private int count = 0;

	/**
	 * This is constructor.
	 * 
	 * @param total
	 */
	public CountBarrier(final int total) {
		this.total = total;
	}

	/**
	 * Increase counter value by one.
	 */
	public void count() {
		synchronized (monitor) {
			this.count += 1;
			monitor.notifyAll();
		}
	}

	/**
	 * Waits until the counter reaches the total value.
	 */
	public void await() {
		synchronized (monitor) {
			while (this.count < total) {
				try {
					monitor.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}
