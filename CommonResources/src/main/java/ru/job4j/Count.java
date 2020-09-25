package ru.job4j;

/**
 * This class demonstrate counter.
 * @author Kirill
 */
public class Count {
    private int value;
    /**
     * increment counter.
     */
    public synchronized void increment() {
        value++;
    }
    /**
     * get counter value.
     * @return count value
     */
    public synchronized int get() {
        return value;
    }
}
