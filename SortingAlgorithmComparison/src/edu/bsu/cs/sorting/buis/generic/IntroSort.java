package edu.bsu.cs.sorting.buis.generic;


import static edu.bsu.cs.sorting.buis.generic.GenericHelpers.lessThan;
import static edu.bsu.cs.sorting.buis.generic.GenericHelpers.swap;
import edu.bsu.cs.sorting.javautil.generic.BinarySort;

public class IntroSort {

	// prevent instantiation
	private IntroSort() {
	}
	
	private static final int INSERTION_SORT_THRESHOLD = 47;
	
	public static <T extends Comparable<? super T>> void sort(T[] array) {
		sort(array, 0, array.length, 2*log2(array.length));
	}	
	
	/**
	 * 
	 * @param n
	 * @return logarithm base 2 of n
	 */
	private static int log2(int n){
	    if(n <= 0) throw new IllegalArgumentException();
	    return 31 - Integer.numberOfLeadingZeros(n);
	}
	
	private static <T extends Comparable<? super T>> int pickPivotIndex(final T[] array, int iStart, int iEnd) {
		// consider median of 3
		// consider median of N/logN ??
		return iStart;
	}
	
	private static <T extends Comparable<? super T>> int partition(T[] array, int iStart, int iEnd, int pivotIndex) {
		iEnd--;
		T pivotValue = array[pivotIndex];
		// swap to store pivotValue in last valid slot in sub-array
		array[pivotIndex] = array[iEnd];
		array[iEnd] = pivotValue;
		
		pivotIndex = iStart;
		
		for(int i=iStart; i<iEnd; i++) {
			if (lessThan(array[i],pivotValue)) {
				swap(array, i, pivotIndex);
				pivotIndex++;
			}
		}
		
		// pivotValue = array[iEnd];
		array[iEnd] = array[pivotIndex];
		array[pivotIndex] = pivotValue;
		return pivotIndex;	
	}
	
	public static <T extends Comparable<? super T>> void sort(T[] array, int iStart, int iEnd, int depthThreshold) {
		int length= iEnd - iStart;
		
		while (length > INSERTION_SORT_THRESHOLD) {
			if (--depthThreshold <= 0) {
				HeapSort.sort(array, iStart, iEnd);
				return;
			}
			int iPivot = pickPivotIndex(array, iStart, iEnd);
			iPivot = partition(array, iStart, iEnd, iPivot);
			if ((iPivot - iStart) /* right length */ < 
				(iEnd - (iPivot+1)) /* left length */) {
				sort(array, iStart, iPivot, depthThreshold);
				iStart = iPivot + 1;
			}
			else {
				sort(array, iPivot+1, iEnd, depthThreshold);
				iEnd = iPivot;
			}
			length = iEnd - iStart;
	    }
		
		BinarySort.sort(array, iStart, iEnd);
	}
}
