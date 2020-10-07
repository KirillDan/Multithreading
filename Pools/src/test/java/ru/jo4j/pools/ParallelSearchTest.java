package ru.jo4j.pools;

import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import org.junit.Test;

import ru.job4j.pools.ParallelSearch;

class TestSearch {
	private Integer id;
	private String value;

	public TestSearch(final Integer id, final String value) {
		super();
		this.id = id;
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		TestSearch other = (TestSearch) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "[" + id + ", " + value + "]";
	}
}

public class ParallelSearchTest {
	
	/**
	 * Test ParallelSearch on int.
	 */
	@Test
	public void intSearch() {
		int searchValueTest = 1;
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		int testArrayLendth = 100;
		Integer[] testArray = new Integer[testArrayLendth];
		for (int i = 0; i < testArray.length; i++) {
			testArray[i] = new Random().nextInt(2);
		}
		int[] resultTest = (int[]) forkJoinPool
				.invoke(new ParallelSearch(testArray, searchValueTest, 0, testArray.length));
		int resultI = -1;
		for (int i = 0; i < testArray.length; i++) {
			if (testArray[i].equals(searchValueTest)) {
				resultI += 1;
				assertEquals(resultTest[resultI], i);				
			}
		}
	}

	/**
	 * Test ParallelSearch on object.
	 */
	@Test
	public void objectSearch() {
		TestSearch searchValueTest = new TestSearch(1, "Hello");
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		int testArrayLendth = 100;
		TestSearch[] testArray = new TestSearch[testArrayLendth];
		for (int i = 0; i < testArray.length; i++) {
			testArray[i] = new TestSearch(new Random().nextInt(2), "Hello");
		}
		int[] resultTest = (int[]) forkJoinPool
				.invoke(new ParallelSearch(testArray, searchValueTest, 0, testArray.length));
		int resultI = -1;
		for (int i = 0; i < testArray.length; i++) {
			if (testArray[i].equals(searchValueTest)) {
				resultI += 1;
				assertEquals(resultTest[resultI], i);				
			}
		}
	}
}
