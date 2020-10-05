package ru.job4j;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

/**
 * Testing Stack.
 * 
 * @author kirill
 *
 */
public class StackTest {
	/**
	 * Testing Stack with 3 threads.
	 */
	@Test
	public void when3PushThen3Poll() {
		Stack<Integer> stack = new Stack<>();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		assertThat(stack.poll(), is(3));
		assertThat(stack.poll(), is(2));
		assertThat(stack.poll(), is(1));
	}

	/**
	 * Testing Stack with 1 threads.
	 */
	@Test
	public void when1PushThen1Poll() {
		Stack<Integer> stack = new Stack<>();
		stack.push(1);
		assertThat(stack.poll(), is(1));
	}

	/**
	 * Testing Stack with 2 threads.
	 */
	@Test
	public void when2PushThen2Poll() {
		Stack<Integer> stack = new Stack<>();
		stack.push(1);
		stack.push(2);
		assertThat(stack.poll(), is(2));
		assertThat(stack.poll(), is(1));
	}
}
