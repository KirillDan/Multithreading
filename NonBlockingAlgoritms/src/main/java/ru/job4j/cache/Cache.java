package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Base {
	private int id;
	private Integer version = 0;
	private String name;

	public Base() {
	}

	public Base(final int id, final String name) {
		this.id = id;
		this.name = name;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int newVersion) {
		this.version = newVersion;
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
		return "Base [id=" + id + ", version=" + version + ", name=" + name + "]\n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		Base other = (Base) obj;
		if (id != other.id) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!version.equals(other.version)) {
			return false;
		}
		return true;
	}

}

/**
 * 
 * @author kirill
 *
 */
public class Cache {
	private ConcurrentHashMap<Integer, Base> cache = new ConcurrentHashMap<Integer, Base>();
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
			if (model.getVersion() != v.getVersion()) {
				throw new OptimisticException("Throw Exception in Thread");
			}
			v.setName(model.getName());
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
