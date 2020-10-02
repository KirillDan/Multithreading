package ru.job4j.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing UserStorage.
 * 
 * @author kirill
 *
 */
public class UserStorageTest {

	private UserStorage userStorage;

	/**
	 * Creating UserStorage.
	 */
	@Before
	public void setup() {
		this.userStorage = new UserStorage();
	}

	/**
	 * Add user, then delete.
	 */
	@Test
	public void testAddingAndDeleting() {
		User user = new User(Math.abs(new Random().nextInt()), Math.abs(new Random().nextInt()));
		this.userStorage.add(user);
		assertNotNull(this.userStorage.get(user.getId()));
		assertEquals(this.userStorage.get(user.getId()), user);
		this.userStorage.delete(user);
		assertNull(this.userStorage.get(user.getId()));
	}

	/**
	 * Add user, then update, then delete.
	 */
	@Test
	public void testAddingAndUpdatingAndDeleting() {
		User user = new User(Math.abs(new Random().nextInt()), Math.abs(new Random().nextInt()));
		this.userStorage.add(user);
		assertEquals(this.userStorage.get(user.getId()), user);
		user.setAmount(Math.abs(new Random().nextInt()));
		this.userStorage.update(user);
		assertEquals(this.userStorage.get(user.getId()), user);
		this.userStorage.delete(user);
		assertNull(this.userStorage.get(user.getId()));
	}

	/**
	 * Add two user, then transfer amount.
	 */
	@Test
	public void testTransfer() {
		int amount1 = Math.abs(new Random().nextInt(Integer.MAX_VALUE / 2));
		int amount2 = Math.abs(new Random().nextInt(Integer.MAX_VALUE / 2));
		int amountTransfer = Math.abs(new Random().nextInt(Integer.MAX_VALUE / 2));
		amount1 += amountTransfer;
		User user1 = new User(Math.abs(new Random().nextInt()), amount1);
		User user2 = new User(Math.abs(new Random().nextInt()), amount2);
		this.userStorage.add(user1);
		this.userStorage.add(user2);
		this.userStorage.transfer(user1.getId(), user2.getId(), amountTransfer);
		assertEquals(this.userStorage.get(user1.getId()).getAmount(), amount1 - amountTransfer);
		assertEquals(this.userStorage.get(user2.getId()).getAmount(), amount2 + amountTransfer);
	}
}
