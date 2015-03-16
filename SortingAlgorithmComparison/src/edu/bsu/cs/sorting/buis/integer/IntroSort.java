package edu.bsu.cs.sorting.buis.integer;



public class IntroSort {

    // prevent instancing
    private IntroSort() {
    }
	
// same as OpenJDK6 QuickSort
// smaller than JDK 7+ DualPivotQuicksort threshold of 47
	private static final int INSERTION_SORT_THRESHOLD = 7;

    public static void sort(int[] array) {
        sort(array, 0, array.length, 0, 8 * log2(array.length));
    }

    /**
     * 
     * @param n
     * @return logarithm base 2 of n
     */
    private static int log2(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        return 31 - Integer.numberOfLeadingZeros(n);
    }

    public static void sort(int[] array, int iStart, int iEnd, int depth,
            int depthThreshold) {
        int length = iEnd - iStart;
        while (length > INSERTION_SORT_THRESHOLD) {
			if (--depthThreshold <= 0) {
                HeapSort.sort(array, iStart, iEnd);
            }
            int iPivot = pickPivotIndex(array, iStart, iEnd);
            iPivot = partition(array, iStart, iEnd, iPivot);
            // recurse on smaller partition
            if ((iPivot - iStart) < (iEnd - (iPivot + 1)))  {
                sort(array, iStart, iPivot, depth + 1, depthThreshold);
                iStart = iPivot + 1;
            } else {
                sort(array, iPivot + 1, iEnd, depth + 1, depthThreshold);
                iEnd = iPivot;
            }
            length = iEnd - iStart;
        }

        if (INSERTION_SORT_THRESHOLD > 1) {
            InsertionSort.sort(array, iStart, iEnd);
        }
    }

    // median of 3 picker, code modified from OpenJDK6 QuickSort
    private static int pickPivotIndex(final int[] array, int iStart, int iEnd) {
    	int len = iEnd - iStart;
		int m = iStart + (len >> 1); // Small arrays, middle element
		if (len > 7) {
			int l = iStart;
			int n = iStart + len - 1;
			m = med3(array, l, m, n); // median of 3
		}
        return m;
    }
   

    private static int partition(int[] array, int iStart, int iEnd,
            int pivotIndex) {
        iEnd--;
        // swap to store pivotValue in last valid slot in sub-array
        int pivotValue = array[pivotIndex];
        array[pivotIndex] = array[iEnd];
        array[iEnd] = pivotValue;
        pivotIndex = iStart;
        // simple left to right sweep to partition
        // no attempt to meet in middle with pivot values on
        // both ends like Engineered version, so we won't perform
        // as well with lots of duplicated values
        for (int i = iStart; i < iEnd; i++) {
            if (array[i] < pivotValue) {
                int temp = array[i];
                array[i] = array[pivotIndex];
                array[pivotIndex] = temp;
                pivotIndex++;
            }
        }


        array[iEnd] = array[pivotIndex];
        array[pivotIndex] = pivotValue;
        return pivotIndex;
    }
    

	/**
	 * Returns the index of the median of the three indexed integers.
	 */
	private static int med3(int x[], int a, int b, int c) {
		return (x[a] < x[b] ? (x[b] < x[c] ? b : x[a] < x[c] ? c : a)
				: (x[b] > x[c] ? b : x[a] > x[c] ? c : a));
	}
}
