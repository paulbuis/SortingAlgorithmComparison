package edu.bsu.cs.sorting.buis.integer;

// static methods for implementing the HeapSort algorithm
// on an array of integers
public class HeapSort {

	// prevent class instantiation
	private HeapSort() {
	}

	public static void sort(int[] array) {
		sort(array, 0, array.length);
	}

	public static void sort(int[] array, int iStart, int iEnd) {
		int length = iEnd - iStart;
		int heapSize = length;
		buildMaxHeap(array, iStart, iEnd);
		for (int iSubrange = length - 1; iSubrange >= 2; iSubrange--) {
			int temp = array[iStart];
			array[iStart] = array[iSubrange + iStart];
			array[iSubrange + iStart] = temp;
			heapSize--;
			maxHeapify(array, iStart, iEnd, iStart, heapSize);
		}
	}

	private static void buildMaxHeap(final int[] array, final int iStart,
			final int iEnd) {
		final int length = iEnd - iStart;
		final int heapSize = length;
		// variables with "Subrange" must have iStart added to them before
		// they can be used as index to array
		for (int iSubrange = length / 2; iSubrange >= 0; iSubrange--) {
			maxHeapify(array, iStart, iEnd, iSubrange, heapSize);
		}
	}

	private static void maxHeapify(final int[] array, final int iStart,
			final int iEnd, int iSubrange, final int heapSize) {
		int iSubrangeLeft = 2 * iSubrange + 1;
		int iSubrangeRight = 2 * iSubrange + 2;
		int iSubrangeLargest;
		while (iSubrangeRight < heapSize) {
			if (iSubrangeLeft < heapSize && // left child exists
					array[iSubrangeLeft + iStart] > array[iSubrange + iStart]) {
				iSubrangeLargest = iSubrangeLeft;
			} else {
				iSubrangeLargest = iSubrange;
			}
			if (array[iSubrangeRight + iStart] > array[iSubrangeLargest
			                                           + iStart]) {
				iSubrangeLargest = iSubrangeRight;
			}
			if (iSubrangeLargest != iSubrange) {
				int temp = array[iSubrange + iStart];
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
