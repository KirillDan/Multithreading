package ru.job4j.concurrent;

/**
 * This class demonstrate interruption.
 * 
 * @author kirill
 */
public class ThreadStop {
	protected ThreadStop() {
	}

	/**
	 * Main method.
	 * @param args console inputs.
	 */
	public static void main(final String[] args) throws InterruptedException {
		Thread progress = new Thread(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					System.out.println("start ...");
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		});
		progress.start();
		Thread.sleep(1000);
		progress.interrupt();
		progress.join();
	}
}
