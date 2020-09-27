package ru.job4j.ref;

/**
 * This class demonstrate demonstrates thread safety issues.
 * @author Kirill
 */
public class ShareNotSafe {
	protected ShareNotSafe() { }
	/**
	 * This is main method.
	 * @param args
	 * @throws InterruptedException
	 */
    public static void main(final String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("name");
        cache.add(user);
        Thread first = new Thread(
                () -> {
                    user.setName("rename");
                }
        );
        first.start();
        first.join();
        System.out.println(cache.findById(1).getName());
        cache.findAll().forEach(userLocal -> System.out.println(userLocal));
    }
}