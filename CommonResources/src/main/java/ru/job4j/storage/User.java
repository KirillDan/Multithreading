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
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return user amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * 
	 * @param amount
	 */
	public void setAmount(final int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", amount=" + amount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (amount != other.amount) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		return true;
	}
}
