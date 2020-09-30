package ru.job4j;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * This class demonstrate counter.
 * 
 * @author Kirill
 */
public class CountTest {

	/**
	 * Класс описывает нить со счетчиком.
	 */
	private final class ThreadCount extends Thread {
		private final Count count;

		private ThreadCount(final Count count) {
			this.count = count;
		}

		@Override
		public void run() {
			this.count.increment();
		}
	}

	/**
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void whenExecute2ThreadThen2() throws InterruptedException {
		// Создаем счетчик.
		final Count count = new Count();
		// Создаем нити.
		Thread first = new ThreadCount(count);
		Thread second = new ThreadCount(count);
		// Запускаем нити.
		first.start();
		second.start();
		// Заставляем главную нить дождаться выполнения наших нитей.
		first.join();
		second.join();
		// Проверяем результат.
		assertThat(count.get(), is(2));

	}
}