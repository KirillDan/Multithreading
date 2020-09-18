package ru.job4j.concurrent;

/**
 * This class outputs to console names of threads.
 * @author kirill
 */
public class ConcurrentOutput {
	protected ConcurrentOutput() { }
/**
 * Main method.
 * @param args console inputs.
 */
    public static void main(final String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();
        second.start();
        System.out.println(Thread.currentThread().getName());
    }
}
