package ru.job4j.concurrent;

/**
 * This class demonstrate interruption.
 * @author kirill
 */
public class ConsoleProgress implements Runnable {
	/** Поле массив из строк для создания эффект крутящегося шара. */
	private final String[] process = {"-", "\\", "|", "/"};
	
	@Override
	public void run() {
		int index = 0;
		while (!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(500);
				System.out.print("\r Loading ... " + process[index % process.length]);
				index += 1;
			} catch (InterruptedException e) {
				e.printStackTrace();
//				Thread.currentThread().interrupt();
			}
        }
	}
	/**
	 * Main method.
	 * @param args console inputs.
	 * @throws InterruptedException 
	 */
	public static void main(final String[] args) throws InterruptedException {
		Thread progress = new Thread(new ConsoleProgress());
        progress.start();
		Thread.sleep(1000);
        progress.interrupt();
	}

}
