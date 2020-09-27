package ru.job4j.ref;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class demonstrate ThreadSafe UserCache.
 * @author Kirill
 */
@ThreadSafe
public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();
    /**
     * This method add User to ConcurrentHashMap.
     * @param user
     */
    public void add(final User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }
    /**
     * This method get User from ConcurrentHashMap by id.
     * @param id
     * @return user
     */
    public User findById(final int id) {
        return User.of(users.get(id).getName());
    }
    /**
     * This method find all Users.
     * @return List of Users
     */
    public List<User> findAll() {
    	List<User> userList = new ArrayList<User>();
    	users.forEach((id, user) -> userList.add(User.of(users.get(id).getName())));
        return userList;
    }
}
