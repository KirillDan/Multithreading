package ru.job4j.pools;

import java.util.concurrent.RecursiveTask;

/**
 * 
 * @author kirill
 *
 * @param <T>
 */
public class ParallelSearch<T> extends RecursiveTask<int[]> {
	/** The array in which to find the value */
	private final T[] array;
	/** The value to find */
	private final T searchValue;
	/** Start of array */
	private final int from;
	/** End of array */
	private final int to;

	/**
	 * Constructor.
	 * 
	 * @param array
	 * @param searchValue
	 */
	public ParallelSearch(final T[] array, final T searchValue, final int from, final int to) {
		super();
		this.array = array;
		this.searchValue = searchValue;
		this.from = from;
		this.to = to;
	}

	
	/**
	 * In this methos if initial parameters from > to then method return null.
	 * If initial parameters from = to then method use comparison with search value.
	 * If fields or initial parameters to - from < 10 method will use linear searching.
	 * If fields or initial parameters to - from > 10 method will use parallel searching.
	 */
	@Override
	protected int[] compute() {
		if (from > to) {
			return null;
		} else if (from == to) {
			int[] resSearch = new int[1];
			if (this.array[from].equals(this.searchValue)) {
				resSearch[0] = from;
				return resSearch;
			} else {
				return null;
			}
		} else
		if ((to - from) <= 10) {
			int size = to - from;
			int[] resSearch = new int[size];
			int iSearch = -1;
			for (int i = from; i < to; i += 1) {
				if (array[i].equals(searchValue)) {
					iSearch += 1;
					resSearch[iSearch] = i;
				}
			}
			if (iSearch != -1) {
				if ((iSearch + 1) < size) {
					int[] resSearchTrim = new int[iSearch + 1];
					for (int i = 0; i < resSearchTrim.length; i++) {
						resSearchTrim[i] = resSearch[i];
					}
					return resSearchTrim;
				} else {
					return resSearch;
				}
			} else {
				return null;
			}

		} else {
			int mid = (from + to) / 2;
			ParallelSearch leftSearch = new ParallelSearch(array, searchValue, from, mid);
			ParallelSearch rightSearch = new ParallelSearch(array, searchValue, mid, to);
			leftSearch.fork();
			rightSearch.fork();
			int[] left = (int[]) leftSearch.join();
			int[] right = (int[]) rightSearch.join();
			int[] result = null;
			int resultSize = 0;
			if (left != null && right != null) {
				resultSize = left.length + right.length;
				result = new int[resultSize];
				for (int i = 0; i < left.length; i++) {
					result[i] = left[i];
				}
				for (int i = 0; i < right.length; i++) {
					result[left.length + i] = right[i];
				}
			} else if (left != null && right == null) {
				resultSize = left.length;
				result = new int[resultSize];
				for (int i = 0; i < left.length; i++) {
					result[i] = left[i];
				}
			} else if (left == null && right != null) {
				resultSize = right.length;
				result = new int[resultSize];
				for (int i = 0; i < right.length; i++) {
					result[i] = right[i];
				}
			}
			return result;
		}
	}
}
