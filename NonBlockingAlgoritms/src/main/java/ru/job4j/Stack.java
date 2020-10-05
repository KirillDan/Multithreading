package ru.job4j;

import net.jcip.annotations.NotThreadSafe;

/**
 * 
 * @author kirill
 *
 * @param <T>
 */
@NotThreadSafe
public class Stack<T> {
	private Node<T> head;

	/**
	 * Push value into stack.
	 * 
	 * @param value
	 */
	public void push(final T value) {
		Node<T> temp = new Node<>(value);
		if (head == null) {
			head = temp;
			return;
		}
		temp.next = head;
		head = temp;
	}

	/**
	 * Get Value from stack.
	 * 
	 * @return T
	 */
	public T poll() {
		Node<T> temp = head;
		if (temp == null) {
			throw new IllegalStateException("Stack is empty");
		}
		head = temp.next;
		temp.next = null;
		return temp.value;
	}

	private static final class Node<T> {
		private final T value;

		private Node<T> next;

		public Node(final T value) {
			this.value = value;
		}
	}
}
