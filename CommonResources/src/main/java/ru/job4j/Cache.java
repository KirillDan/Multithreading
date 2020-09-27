package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * This class demonstrate cache.
 * @author Kirill
 */
@ThreadSafe
public final class Cache {
	private final ConcurrentHashMap<Integer, String> dic = new ConcurrentHashMap<>();
    private final AtomicInteger ids = new AtomicInteger();  
    /**
     * This is constructor.
     */
    public Cache() {
        dic.put(ids.incrementAndGet(), "Petr Arsentev");
        dic.put(ids.incrementAndGet(), "Ivan Ivanov");
    }
    /**
     * This function add name to ConcurrentHashMap.
     * @param name
     */
    public void add(final String name) {
        dic.put(ids.incrementAndGet(), name);
    }
    /**
     * This function check contain a name value.
     * @param name value
     * @return is contains string
     */
    public boolean contains(final String name) {
        return dic.containsValue(name);
    }
}
