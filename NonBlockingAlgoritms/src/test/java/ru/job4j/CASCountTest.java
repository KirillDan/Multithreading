package ru.job4j;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing CASCount.
 * 
 * @author kirill
 *
 */
public class CASCountTest {
	private CASCount casCount;

	/**
	 * Setup testing variables.
	 */
	@Before
	public void setup() {
		this.casCount = new CASCount();
	}

	/**
	 * Test CASCount with 20 threads.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	@Test
	public void test20Threads() throws InterruptedException {
		int end = 20;
		Thread[] thread = new Thread[end];
		for (int i = 0; i < end; i++) {
			thread[i] = new Thread(() -> {
				this.casCount.increment();
			});
		}
		for (int i = 0; i < end; i++) {
			thread[i].start();
		}
		for (int i = 0; i < end; i++) {
			thread[i].join();
		}
		assertEquals(this.casCount.get(), end);
	}
}
