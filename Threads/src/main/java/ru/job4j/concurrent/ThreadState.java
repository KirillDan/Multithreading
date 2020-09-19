package ru.job4j.concurrent;

/**
 * This class outputs to console names of threads.
 * @author kirill
 */
public class ThreadState {
protected ThreadState() { }
/**
 * Main method.
 * @param args console inputs.
 */
    public static void main(final String[] args) {
        Thread first = new Thread(
                () -> { }
        );
        Thread second = new Thread(
                () -> { }
        );
//        System.out.println(first.getState());
        System.out.println("Name: " + first.getName()
        			+ " | State: " + first.getState());
        System.out.println("Name: " + second.getName()
        			+ " | State: " + second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED 
        		&& second.getState() != Thread.State.TERMINATED) {
        	System.out.println("Name: " + first.getName()
        				+ " | State: " + first.getState());
            System.out.println("Name: " + second.getName()
            			+ " | State: " + second.getState());
        }
        System.out.println("Work completed");
    }
}
