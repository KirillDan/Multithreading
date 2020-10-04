package ru.job4j;

/**
 * This class demonstrait working with Barrier.
 * @author kirill
 *
 */
public class MultiUser {
	protected MultiUser() { }
	/**
	 * Main method.
	 * @param args
	 */
    public static void main(final String[] args) {
        Barrier barrier = new Barrier();
        Thread master = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    barrier.on();
                },
                "Master"
        );
        Thread slave = new Thread(
                () -> {
                    barrier.check();
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Slave"
        );
        master.start();
        slave.start();
    }
}
