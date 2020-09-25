package ru.job4j;

/**
 * This class demonstrate counter.
 * @author Kirill
 */
public final class Cache {
	private Cache() { }
    private static Cache cache;
    /**
     * @return cache Instance
     */
    public static synchronized Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
