package ru.job4j.linked;

import net.jcip.annotations.Immutable;

/**
 * This class demonstrate immutable singly linked list node.
 * @author Kirill
 * @param <T> is type of value
 */
@Immutable
public class Node<T> {
    private final Node next;
    private final T value;
    /**
     * This is constructor.
     */
    public Node(final Node next, final T value) {
		this.next = next;
		this.value = value;
	}
	/**
     * This function get next node.
     * @return next node
     */
    public Node getNext() {
        return next;
    }
    /**
     * This function get value.
     * @return next value
     */
    public T getValue() {
        return value;
    }
}
