package edu.bsu.cs.sorting.buis.generic;

import static edu.bsu.cs.sorting.buis.generic.GenericHelpers.greaterThan;

// static methods for implementing the HeapSort algorithm
// on an array of integers
public class HeapSort {

	// prevent class instantiation
	private HeapSort() {
	}

	public static <T extends Comparable<? super T>> void sort(T[] array) {
		sort(array, 0, array.length);
	}

	public static <T extends Comparable<? super T>> void sort(T[] array, int iStart, int iEnd) {
		int length = iEnd - iStart;
		int heapSize = length;
		buildMaxHeap(array, iStart, iEnd);
		for (int iSubrange = length - 1; iSubrange >= 2; iSubrange--) {
			T temp = array[iStart];
			array[iStart] = array[iSubrange + iStart];
			array[iSubrange + iStart] = temp;
			heapSize--;
			maxHeapify(array, iStart, iEnd, iStart, heapSize);
		}
	}

	private static <T extends Comparable<? super T>> void buildMaxHeap(final T[] array, final int iStart,
			final int iEnd) {
		final int length = iEnd - iStart;
		final int heapSize = length;
		// variables with "Subrange" must have iStart added to them before
		// they can be used as index to array
		for (int iSubrange = length / 2; iSubrange >= 0; iSubrange--) {
			maxHeapify(array, iStart, iEnd, iSubrange, heapSize);
		}
	}

	private static <T extends Comparable<? super T>> void maxHeapify(final T[] array, final int iStart,
			final int iEnd, int iSubrange, final int heapSize) {
		int iSubrangeLeft = 2 * iSubrange + 1;
		int iSubrangeRight = 2 * iSubrange + 2;
		int iSubrangeLargest;
		while (iSubrangeRight < heapSize) {
			if (iSubrangeLeft < heapSize && // left child exists
					greaterThan(array, iSubrangeLeft + iStart, iSubrange + iStart)) {
				iSubrangeLargest = iSubrangeLeft;
			} else {
				iSubrangeLargest = iSubrange;
			}
			if (greaterThan(array, iSubrangeRight + iStart, iSubrangeLargest+ iStart)) {
				iSubrangeLargest = iSubrangeRight;
			}
			if (iSubrangeLargest != iSubrange) {
				T temp = array[iSubrange + iStart];
				array[iSubrange + iStart] = array[iSubrangeLargest + iStart];
				array[iSubrangeLargest + iStart] = temp;
				// tail recursion elimination
				iSubrange = iSubrangeLargest;
				iSubrangeLeft = 2 * iSubrange + 1;
				iSubrangeRight = 2 * iSubrange + 2;
			} else {
				return;
			}
		}
	}

}
