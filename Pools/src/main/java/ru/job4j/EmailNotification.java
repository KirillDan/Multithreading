package ru.job4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class demonstrate working with ExecutorService.
 * @author kirill
 *
 */
public class EmailNotification {
	private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	/**
	 * Execution send method using ExecutorService pool.
	 * @param user
	 */
	public void emailTo(final User user) {
		String subject = "Notification " + user.getUsername() + " to email {email}";
		String body = "Add a new event to " + user.getUsername();
		pool.submit(new Runnable() {
	        @Override
	        public void run() {
	        	send(subject, body, user.getEmail());
	        }
	    });
	}
	/**
	 * 
	 * @param suject
	 * @param body
	 * @param email
	 */
	public void send(final String suject, final String body, final String email) {
	}
	/**
	 * Close pool.
	 */
	public void close() {
		pool.shutdown();
	    while (!pool.isTerminated()) {
	        try {
	            Thread.sleep(100);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
