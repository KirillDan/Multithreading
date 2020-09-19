package ru.job4j.concurrent;

/**
 * This class outputs to console names of threads.
 * @author kirill
 */
public class ThreadSleep {
protected ThreadSleep() { }
/**
 * Main method.
 * @param args console inputs.
 */
	public static void main(final String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ... ");
                        Thread.sleep(3000);
                        System.out.println("Loaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
        System.out.println("Main");
    }
}
