package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

class Base {
	private int id;
	private final AtomicReference<Integer> version = new AtomicReference<>(0);
	private String name;

	public Base() {
		super();
	}

	public Base(final int id, final String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getVersion() {
		return this.version.get();
	}

	public boolean setVersion(final int expectedVersion, final int newVersion) {
		return this.version.compareAndSet(expectedVersion, newVersion);
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Base [id=" + id + ", version=" + version.get() + ", name=" + name + "]\n";
	}

}

/**
 * 
 * @author kirill
 *
 */
public class Cache {
	private ConcurrentHashMap<Integer, Base> cache;

	/**
	 * 
	 */
	public Cache() {
		super();
		this.cache = new ConcurrentHashMap<Integer, Base>();
	}

	/**
	 * 
	 * @param model
	 * @return b
	 */
	public Base add(final Base model) {
		return this.cache.putIfAbsent(model.getId(), model);
	}

	/**
	 * 
	 * @param model
	 */
	public void update(final Base model) throws OptimisticException {
		this.cache.computeIfPresent(model.getId(), (k, v) -> {
			if (!v.setVersion(v.getVersion(), v.getVersion() + 1)) {
				throw new OptimisticException("Throw Exception in Thread");
			} else {
				v.setName(model.getName());
			}
			return v;
		});
	}

	/**
	 * 
	 * @param model
	 * @return b
	 */
	public boolean delete(final Base model) {
		return this.cache.remove(model.getId(), model);

	}

	@Override
	public String toString() {
		String out = "";
		for (Map.Entry<Integer, Base> m : this.cache.entrySet()) {
			out += m.getValue().toString();
		}
		return out;
	}

}
