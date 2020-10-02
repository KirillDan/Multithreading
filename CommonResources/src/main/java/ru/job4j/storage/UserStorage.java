package ru.job4j.storage;

import java.util.concurrent.ConcurrentHashMap;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class UserStorage {
	private ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

	/**
	 * 
	 * @param user
	 * @return add user is true
	 */
	public synchronized boolean add(final User user) {
		boolean result = false;
		if (this.users.putIfAbsent(user.getId(), user) == user) {
			result = true;
		}
		return result;
	}
	/**
	 * 
	 * @param id
	 * @return user
	 */
	public synchronized User get(final int id) {
		return this.users.get(id);
	}
	/**
	 * 
	 * @param user
	 * @return update user is true
	 */
	public synchronized boolean update(final User user) {
		boolean result = false;
		if (this.users.containsKey(user.getId())) {
			this.users.put(user.getId(), user);
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
		return this.users.remove(user.getId(), user);
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
		User fromUser = this.users.get(fromId);
		User toUser = this.users.get(toId);
		if (fromUser != null &&  toUser != null) {
			if (fromUser.getAmount() >= amount) {
				fromUser.setAmount(fromUser.getAmount() - amount);
				toUser.setAmount(toUser.getAmount() + amount);
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
