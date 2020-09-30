package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * This class demonstrate counter.
 * @author Kirill
 */
@ThreadSafe
public class Count {
	@GuardedBy("this")
    private volatile int value;
    /**
     * increment counter.
     */
    public synchronized void increment() {
        this.value++;
    }
    /**
     * get counter value.
     * @return count value
     */
    public synchronized int get() {
        return this.value;
    }
}
