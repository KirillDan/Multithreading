package ru.job4j.storage;

import java.util.concurrent.ConcurrentHashMap;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class UserStorage {
	private volatile ConcurrentHashMap<Integer, Integer> users = new ConcurrentHashMap<>();
	/**
	 * 
	 * @param user
	 * @return add user is true
	 */
	public synchronized boolean add(final User user) {
		boolean result = false;
		if (!this.users.containsKey(user.getId())) {
			this.users.put(user.getId(), user.getAmount());
			result = true;
		}	
		return result;
	}
	/**
	 * 
	 * @param user
	 * @return update user is true
	 */
	public synchronized boolean update(final User user) {
		boolean result = false;
		if (this.users.containsKey(user.getId())) {
			this.users.put(user.getId(), user.getAmount());
			result = true;
		}		
		return result;
	}
	/**
	 * 
	 * @param user
	 * @return delete user is true
	 */
	public synchronized boolean delete(final User user) {
		return this.users.remove(user.getId(), user.getAmount());
	}
	/**
	 * 
	 * @param fromId
	 * @param toId
	 * @param amount
	 * @return transfer user's amount is true
	 */
	public synchronized boolean transfer(final int fromId, final int toId, final int amount) {
		boolean result = false;
		if (this.users.containsKey(fromId)
				&& this.users.containsKey(toId)) {
			Integer fromAmount = this.users.get(fromId);
			Integer toAmount = this.users.get(toId);
			if (fromAmount >= amount) {
				fromAmount -= amount;
				toAmount += amount;
				this.users.put(fromId, fromAmount);
				this.users.put(toId, toAmount);
				result = true;
			}
		}
		return result;
	}
	@Override
	public String toString() {
		return "UserStorage  =  " + users.toString();
	}
}
