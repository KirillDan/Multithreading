package ru.job4j;

/**
 * This class demonstrate atomicity.
 * @author kirill
 */
public class CountShareMain {
	protected CountShareMain() { }
	/**
	 * Main method.
	 * @param args console inputs.
	 * @throws InterruptedException 
	 */
    public static void main(final String[] args) throws InterruptedException {
        Count count = new Count();
        Thread first = new Thread(count::increment);
        Thread second = new Thread(count::increment);
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(count.get());
    }
}
