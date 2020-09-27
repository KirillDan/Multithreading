package ru.job4j.ref;

/**
 * This class demonstrate mutable User.
 * @author Kirill
 */
public class User {
    private int id;
    private String name;
    /**
     * This function create user.
     * @param name
     * @return new User with name
     */
    public static User of(final String name) {
        User user = new User();
        user.name = name;
        return user;
    }
    /**
     * This method get id by this User.
     * @return User id
     */
    public int getId() {
        return id;
    }
    /**
     * This method set id this User.
     * @param id
     */
    public void setId(final int id) {
        this.id = id;
    }
    /**
     * This method get name by this User.
     * @return User name
     */
    public String getName() {
        return name;
    }
    /**
     * This method set name this User.
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
}
