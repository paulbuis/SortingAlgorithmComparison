package edu.bsu.cs.sorting.buis.integer;



public class IntroSort {

    // prevent instancing
    private IntroSort() {
    }

    public static void sort(int[] array) {
        final int minPartitionSize = 1;
        sort(array, 0, array.length, 0, 2 * log2(array.length),
                minPartitionSize);
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
            int maxDepth, int minPartitionSize) {
        int length = iEnd - iStart;
        while (length > minPartitionSize) {
            if (depth > maxDepth) {
                HeapSort.sort(array, iStart, iEnd);
            }
            int iPivot = pickPivotIndex(array, iStart, iEnd);
            iPivot = partition(array, iStart, iEnd, iPivot);
            if ((iPivot - iStart) /* right length */< (iEnd - (iPivot + 1)) /*
                                                                         * left
                                                                         * length
                                                                         */) {
                sort(array, iStart, iPivot, depth + 1, maxDepth,
                        minPartitionSize);
                iStart = iPivot + 1;
            } else {
                sort(array, iPivot + 1, iEnd, depth + 1, maxDepth,
                        minPartitionSize);
                iEnd = iPivot;
            }
            depth = depth + 1;
            length = iEnd - iStart;
        }

        if (minPartitionSize > 1) {
            InsertionSort.sort(array, iStart, iEnd);
        }
    }

    private static int pickPivotIndex(final int[] array, int iStart, int iEnd) {
        // consider median of 3
        // consider median of N/logN ??
        return iStart;
    }

    private static int partition(int[] array, int iStart, int iEnd,
            int pivotIndex) {
        iEnd--;
        int pivotValue = array[pivotIndex];
        // swap to store pivotValue in last valid slot in sub-array
        array[pivotIndex] = array[iEnd];
        array[iEnd] = pivotValue;

        pivotIndex = iStart;

        for (int i = iStart; i < iEnd; i++) {
            if (array[i] < pivotValue) {
                int temp = array[i];
                array[i] = array[pivotIndex];
                array[pivotIndex] = temp;
                pivotIndex++;
            }
        }

        // pivotValue = array[iEnd];
        array[iEnd] = array[pivotIndex];
        array[pivotIndex] = pivotValue;
        return pivotIndex;
    }
}
