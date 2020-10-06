package ru.job4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class User {
	private String username;
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}
}

/**
 * This class demonstrate working with ExecutorService.
 * @author kirill
 *
 */
public class EmailNotification {
	private ExecutorService pool;
	/**
	 * Constructor setup ExecutorService.
	 */
	public EmailNotification() {
		this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	}
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
