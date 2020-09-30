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
    public void increment() {
    	synchronized (this) {
            value++;
        }
    }
    /**
     * get counter value.
     * @return count value
     */
    public int get() {
    	synchronized (this) {
            return value;
        }
    }
}
