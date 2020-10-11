package ru.job4j.cache;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing Cache class.
 * @author kirill
 *
 */
public class CacheTest {
	private Cache cache;

	/**
	 * Setup testing.
	 */
	@Before
	public void setup() {
		this.cache = new Cache();
	}

	/**
	 * Testing with 160x160 threads.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	@Test
	public void testWith160x160Threads() throws InterruptedException {
		AtomicReference<Exception> ex = new AtomicReference<>();
		int end = 160;
		Base[] base = new Base[end];
		for (int i = 0; i < end; i++) {
			base[i] = new Base(i, "Name" + i);
		}
		for (int i = 0; i < end; i++) {
			this.cache.add(base[i]);
		}
		Base[][] newBase = new Base[end][end];
		for (int j = 0; j < end; j++) {
			for (int i = 0; i < end; i++) {
				newBase[i][j] = new Base(j, "Name" + 10 + 10 * j + i);
			}
		}
		Thread[][] thread = new Thread[end][end];
		for (int j = 0; j < end; j++) {
			for (int i = 0; i < end; i++) {
				thread[i][j] = new Thread() {
					private Cache cache;
					private int i;
					private int j;

					public Thread setup(final Cache cache, final int i, final int j) {
						this.cache = cache;
						this.i = i;
						this.j = j;
						return this;
					}

					@Override
					public void run() {
						try {
							this.cache.update(newBase[i][j]);
						} catch (Exception e) {
							ex.set(e);
						}
					}

				}.setup(this.cache, i, j);
			}
		}
		for (int j = 0; j < end; j++) {
			for (int i = 0; i < end; i++) {
				thread[i][j].start();
			}
		}
		for (int j = 0; j < end; j++) {
			for (int i = 0; i < end; i++) {
				thread[i][j].join();
			}
		}
		Assert.assertNull(ex.get());
	}
}
