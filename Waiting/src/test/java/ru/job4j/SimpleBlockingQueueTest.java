package ru.job4j;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {
	/**
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void whenFetchAllThenGetIt() throws InterruptedException {
		final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
		final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
		Thread producer = new Thread(() -> {
			IntStream.range(0, 5).forEach(queue::offer);
		});
		producer.start();
		Thread consumer = new Thread(() -> {
			while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
				try {
					buffer.add(queue.poll());
				} catch (InterruptedException e) {
//					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		});
		consumer.start();
		producer.join();
		consumer.interrupt();
		consumer.join();
		assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
	}

	/**
	 * @throws InterruptedException 
	 * 
	 */
	@Test
	public void test() throws InterruptedException {
		final SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>();
		final CopyOnWriteArrayList<String> buffer = new CopyOnWriteArrayList<>();
		Thread consumer = new Thread(() -> {
			while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
				try {
					buffer.add(queue.poll());
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		});
		consumer.start();
		Thread producer = new Thread(() -> {
			for (int i = 0; i < 3; i += 1) {
				queue.offer("Hello" + i);
			}
		});
		producer.start();
		producer.join();
		consumer.interrupt();
		consumer.join();
		assertThat(buffer, is(Arrays.asList("Hello0", "Hello1", "Hello2")));
	}
}
