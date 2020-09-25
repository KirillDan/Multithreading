package ru.job4j.concurrent;

/**
 * This class outputs to console names of threads.
 * @author kirill
 */
public class Wget {
protected Wget() { }
/**
 * Main method.
 * @param args console inputs.
 */
	public static void main(final String[] args) {
		Thread thread = new Thread(() -> {
			for (int index = 0; index < 101; index++) {
				try {
					Thread.sleep(1000);
					System.out.print("\rLoading : " + index  + "%");
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		});
		thread.start();
	}

}
