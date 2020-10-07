package ru.job4j.pool;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class ThreadPoolTest {
	private ThreadPool threadPool;
	private List<Integer> list;
	/**
	 * 
	 */
	@Before
	public void setup() {
		this.threadPool = new ThreadPool();
		this.list = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			this.list.add(new Random().nextInt());
		}
	}
	
	/**
	 * @throws InterruptedException 
	 * 
	 */
	@Test
	public void testwithList() throws InterruptedException {
		List<Integer> newList = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			Integer j = i;
			this.threadPool.work(() -> {
				newList.add(this.list.get(j));
			});
		}
		Thread.sleep(1000);
		threadPool.shutdown();
		for (int i = 0; i < 10; i++) {
			assertEquals(newList.get(i), this.list.get(i));
		}
	}
}
