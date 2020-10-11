package ru.job4j.pools;

public class MergeSort {
	protected MergeSort() {
	}

	/**
	 * 
	 * @param array
	 * @return int[]
	 */
	public static int[] sort(final int[] array) {
		return sort(array, 0, array.length - 1);
	}

	private static int[] sort(final int[] array, final int from, final int to) {
		if (from == to) {
			return new int[] {array[from]};
		}
		int mid = (from + to) / 2;
		return merge(
				sort(array, from, mid),
				sort(array, mid + 1, to));
	}

	/**
	 * Метод объединения двух отсортированных массивов.
	 * 
	 * @param left
	 * @param right
	 * @return int[]
	 */
	public static int[] merge(final int[] left, final int[] right) {
		int li = 0;
		int ri = 0;
		int resI = 0;
		int[] result = new int[left.length + right.length];
		while (resI != result.length) {
			if (li == left.length) {
				result[resI++] = right[ri++];
			} else if (ri == right.length) {
				result[resI++] = left[li++];
			} else if (left[li] < right[ri]) {
				result[resI++] = left[li++];
			} else {
				result[resI++] = right[ri++];
			}
		}
		return result;
	}

}
