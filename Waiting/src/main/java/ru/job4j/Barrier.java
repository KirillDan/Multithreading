package ru.job4j;

/**
 * This class realize waiting Barrier.
 * @author kirill
 *
 */
public class Barrier {
    private boolean flag = false;
    
    private final Object monitor = this;
    /**
     * This method on waiting.
     */
    public void on() {
        synchronized (monitor) {
            flag = true;
            monitor.notifyAll();
        }
    }
    /**
     * This method off waiting.
     */
    public void off() {
        synchronized (monitor) {
            flag = false;
            monitor.notifyAll();
        }
    }
    /**
     * This method realize waiting.
     */
    public void check() {
        synchronized (monitor) {
            while (!flag) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
