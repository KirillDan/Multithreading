package ru.job4j;

/**
 * This class demonstrate volatile synchronization.
 * @author Kirill
 */
public class Flag {
	protected Flag() { }
    private static volatile boolean flag = true;
    /**
	 * Main method.
	 * @param args console inputs.
	 * @throws InterruptedException 
	 */
    public static void main(final String[] args) throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    while (flag) {
                        System.out.println(Thread.currentThread().getName());
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        thread.start();
        Thread.sleep(1000);
        flag = false;
        thread.join();
    }
}
