package ru.job4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author kirill
 *
 */
public class SimpleBlockingQueueTestString {
	private int total;
	private SimpleBlockingQueue<String> simpleBlockingQueue;

	/**
	 * 
	 */
	@Before
	public void setup() {
		this.total = 5;
		this.simpleBlockingQueue = new SimpleBlockingQueue<String>(this.total);
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	@Test
	public void testQueueWithoutBlocking() throws InterruptedException {
		String message = "Hello";
		Thread producer = new Thread(() -> {
			this.simpleBlockingQueue.offer(message);
		});
		producer.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		Thread consumer = new Thread(() -> {
			assertEquals(this.simpleBlockingQueue.poll(), message);
		});
		consumer.start();
		consumer.join();
		producer.join();
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	@Test
	public void testQueueWithBlockingConsumer() throws InterruptedException {
		String message = "Hello";
		Thread consumer = new Thread(() -> {
			assertEquals(this.simpleBlockingQueue.poll(), message);
		});
		consumer.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		assertTrue(consumer.isAlive());
		Thread producer = new Thread(() -> {
			this.simpleBlockingQueue.offer(message);
		});
		producer.start();
		consumer.join();
		producer.join();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		assertFalse(consumer.isAlive());
	}
	/**
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testQueueWithBlockingProducer() throws InterruptedException {
		String message = "Hello";
		Thread producer = new Thread(() -> {
			for (int i = 0; i < this.total + 1; i += 1) {
				this.simpleBlockingQueue.offer(message);
			}
		});
		producer.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		assertTrue(producer.isAlive());
		Thread consumer = new Thread(() -> {
				this.simpleBlockingQueue.poll();
		});
		consumer.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		assertFalse(producer.isAlive());
		
	}
}
