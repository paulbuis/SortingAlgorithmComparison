package edu.bsu.cs.sorting.buis.generic;

public class GenericHelpers {

	// prevent instancing
	private GenericHelpers() {
	}

	public static <T extends Comparable<? super T>> boolean greaterThan(T a, T b) {
		return a.compareTo(b) > 0;
	}
	
	public static <T extends Comparable<? super T>> boolean greaterThan(T[] array, int i, int j) {
		return array[i].compareTo(array[j]) > 0;
	}
	
	public static <T extends Comparable<? super T>> boolean lessThan(T a, T b) {
		return a.compareTo(b) < 0;
	}
	
	public static <T extends Comparable<? super T>> boolean equalTo(T a, T b) {
		return a.compareTo(b) == 0;
	}
	
	// like comparisons, should be inlined, so no performance penalty for
	// abstracting it into a function call

	public static <T> void swap(T[] array, int i, int j) {
		T temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}	

