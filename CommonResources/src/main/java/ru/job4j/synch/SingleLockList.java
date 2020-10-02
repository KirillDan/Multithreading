package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.NoSuchElementException;

interface Element<T> {
	public T getValue();

	public void setValue(final T value);

	public Element<T> getNext();

	public void setNext(final Element<T> next);

	public Element<T> getPrev();

	public void setPrev(final Element<T> prev);
}

class StartElement<T> implements Element<T> {
	private Element<T> next;
	private Element<T> prev;

	public StartElement() {
		this.next = null;
		this.prev = null;
	}

	public Element<T> getNext() {
		return next;
	}

	public void setNext(final Element<T> next) {
		this.next = next;
	}

	public Element<T> getPrev() {
		return prev;
	}

	public void setPrev(final Element<T> prev) {
		this.prev = prev;
	}

	@Override
	public T getValue() {
		return null;
	}

	@Override
	public void setValue(final T value) {
	}
}

class EntryElement<T> implements Element<T> {
	private T value;
	private Element<T> next;
	private Element<T> prev;

	public EntryElement(final T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(final T value) {
		this.value = value;
	}

	public Element<T> getNext() {
		return next;
	}

	public void setNext(final Element<T> next) {
		this.next = next;
	}

	public Element<T> getPrev() {
		return prev;
	}

	public void setPrev(final Element<T> prev) {
		this.prev = prev;
	}

	@Override
	public String toString() {
		return "[value=" + value + ", next=" + next.getValue() + ", prev=" + prev.getValue() + "]";
	}

}

interface DynamicList<T> extends Iterable<T>  {
	public Element<T> add(T value);

	public T get(int index);
}

class DynamicListImpl<T> implements DynamicList<T> {
	private StartElement<T> startElement;

	public DynamicListImpl() {
		this.startElement = new StartElement<T>();
		this.startElement.setNext(this.startElement);
		this.startElement.setPrev(this.startElement);
	}

	@Override
	public Element<T> add(final T value) {
		Element<T> result = null;
		Element<T> entry = (Element<T>) new EntryElement<T>(value);
		if (this.startElement.getNext() == this.startElement) {
			this.startElement.setNext(entry);
			this.startElement.setPrev(entry);
			entry.setNext(this.startElement);
			entry.setPrev(this.startElement);
			result = entry;
		} else {
			Element<T> findElement = this.startElement.getNext();
			while (findElement.getNext() != this.startElement) {
				findElement = findElement.getNext();
			}
			findElement.setNext(entry);
			entry.setPrev(findElement);
			entry.setNext(this.startElement);
			this.startElement.setPrev(entry);
			result = entry;
		}
		return result;
	}

	@Override
	public T get(final int index) {
		T result = null;
		Element<T> findEntry = this.startElement;
		if (this.startElement.getNext() != this.startElement) {
			for (int i = 0; i < index + 1; i += 1) {
				findEntry = findEntry.getNext();
				if (findEntry == this.startElement) {
					return null;
				}
			}
			result = findEntry.getValue();
		}
		return result;
	}

	@Override
	public String toString() {
		String result = "";
		Element<T> findElement = this.startElement.getNext();
		if (this.startElement.getNext() != this.startElement) {
			while (findElement != this.startElement) {
				result = result + findElement.toString() + "\n";
				findElement = findElement.getNext();
			}
		}
		return result;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new IteratorDynamicList<T>(this.startElement);
	}
}

class IteratorDynamicList<T> implements Iterator<T> {
	private Element<T> startElement;
	private Element<T> currentElenemt;

	public IteratorDynamicList(final Element<T> startElement) {
		this.startElement = startElement;
		this.currentElenemt = startElement;
	}

	@Override
	public boolean hasNext() {
		boolean result = false;
		if (this.currentElenemt.getNext() != this.startElement) {
			result = true;
		}
		return result;
	}

	@Override
	public T next() {
		T result = null;
		if (!this.hasNext()) {
			throw new NoSuchElementException("No Elements");
		} else {
			this.currentElenemt = this.currentElenemt.getNext();
			result = this.currentElenemt.getValue();
		}
		return result;
	}
}

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
	@GuardedBy("this")
	private final DynamicList<T> list = new DynamicListImpl<T>();

	/**
	 * 
	 * @param value
	 */
	public synchronized void add(final T value) {
		this.list.add(value);
	}

	/**
	 * 
	 * @param index
	 * @return t
	 */
	public synchronized T get(final int index) {
		return this.list.get(index);
	}
	
	@Override
	public synchronized Iterator<T> iterator() {
		Iterator<T> iterator = this.list.iterator();
		DynamicList<T> listIn = new DynamicListImpl<T>();
		while (iterator.hasNext()) {
			listIn.add(iterator.next());
		}		
		return listIn.iterator();
	}
}
