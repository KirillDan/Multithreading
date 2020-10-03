package ru.job4j;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CountBarrierTest {
	private int total;
	private CountBarrier countBarrier;

	/**
	 * 
	 */
	@Before
	public void setup() {
		this.total = 5;
		this.countBarrier = new CountBarrier(total);
	}

	/**
	 * 
	 */
	@Test
	public void testWithSlaveThreadAndMasterTreads() {
		Thread slave = new Thread(() -> {
			this.countBarrier.await();
		});
		slave.start();
		for (int i = 0; i < this.total; i += 1) {
			assertTrue(slave.isAlive());
			new Thread(() -> {
				this.countBarrier.count();
			}).start();
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		assertFalse(slave.isAlive());
	}
}
