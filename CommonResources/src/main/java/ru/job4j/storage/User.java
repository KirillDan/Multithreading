package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * 
 * @author kirill
 */
@ThreadSafe
public class User {
	@GuardedBy("this")
	private volatile int id;
	@GuardedBy("this")
	private volatile int amount;
	/**
	 * 
	 * @param id
	 * @param amount
	 */
	public User(final int id, final int amount) {
		super();
		this.id = id;
		this.amount = amount;
	}
	/**
	 * 
	 * @return user id
	 */
	public synchronized int getId() {
		return id;
	}
	/**
	 * 
	 * @param id
	 */
	public synchronized void setId(final int id) {
		this.id = id;
	}
	/**
	 * 
	 * @return user amount
	 */
	public synchronized int getAmount() {
		return amount;
	}
	/**
	 * 
	 * @param amount
	 */
	public synchronized void setAmount(final int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", amount=" + amount + "]";
	}
}
