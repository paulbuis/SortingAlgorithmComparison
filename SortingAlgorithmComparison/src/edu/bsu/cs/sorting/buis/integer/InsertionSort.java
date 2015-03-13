package edu.bsu.cs.sorting.buis.integer;

public class InsertionSort {

	// prevent instantiation
	private InsertionSort() {
	}

	public static void sort(final int[] array) {
		sort(array, 0, array.length);
	}
	
	// adapted from TimSort.binarySort
	@SuppressWarnings("fallthrough")
	public static void sort(int[] a,
			int lo, int hi) {
		
		for (int start = lo+1; start < hi; start++) {
			int pivot = a[start];

			// Set left (and right) to the index where a[start] (pivot) belongs
			int left = lo;
			int right = start;
			assert left <= right;
			/*
			 * Invariants: pivot >= all in [lo, left). pivot < all in [right,
			 * start).
			 */
			while (left < right) {
				int mid = (left + right) >>> 1;
				if (pivot < a[mid])
					right = mid;
				else
					left = mid + 1;
			}
			assert left == right;

			/*
			 * The invariants still hold: pivot >= all in [lo, left) and pivot <
			 * all in [left, start), so pivot belongs at left. Note that if
			 * there are elements equal to pivot, left points to the first slot
			 * after them -- that's why this sort is stable. Slide elements over
			 * to make room for pivot.
			 */
			int n = start - left; // The number of elements to move
			// Switch is just an optimization for arraycopy in default case
			switch (n) {
			case 2:
				a[left + 2] = a[left + 1];
			case 1:
				a[left + 1] = a[left];
				break;
			default:
				System.arraycopy(a, left, a, left + 1, n);
			}
			a[left] = pivot;
		}
	}
}
