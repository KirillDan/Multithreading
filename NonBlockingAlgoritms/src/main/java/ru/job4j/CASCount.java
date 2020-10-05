package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Not blocking counter.
 * 
 * @author kirill
 *
 */
@ThreadSafe
public class CASCount {
	private final AtomicReference<Integer> count = new AtomicReference<>(0);

	/**
	 * Increase value by oneю
	 */
	public void increment() {
		Integer expectedValue;
		Integer newValue;
		do {
			expectedValue = this.count.get();
			newValue = expectedValue + 1;
		} while (!this.count.compareAndSet(expectedValue, newValue));
	}

	/**
	 * Get value of count.
	 * 
	 * @return count value
	 */
	public int get() {
		return this.count.get();
	}
}
