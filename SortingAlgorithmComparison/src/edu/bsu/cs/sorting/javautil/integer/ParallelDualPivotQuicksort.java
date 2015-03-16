package edu.bsu.cs.sorting.javautil.integer;

import java.util.concurrent.ForkJoinPool;

public class ParallelDualPivotQuicksort {
	/**
	 * The minimum array length below which a parallel sorting algorithm will
	 * not further partition the sorting task. Using smaller sizes typically
	 * results in memory contention across tasks that makes parallel speedups
	 * unlikely.
	 */
	private static final int MIN_ARRAY_SORT_GRAN = 1 << 13;

	private ParallelDualPivotQuicksort() {
	}

	public static void sort(int[] a) {
		int n = a.length, p, g;
		if (n <= MIN_ARRAY_SORT_GRAN
				|| (p = ForkJoinPool.getCommonPoolParallelism()) == 1)
			DualPivotQuicksort.sort(a, 0, n - 1);
		else
			new ArraysParallelSortHelpers.FJInt.Sorter(
					null,
					a,
					new int[n],
					0,
					n,
					0,
					((g = n / (p << 2)) <= MIN_ARRAY_SORT_GRAN) ? MIN_ARRAY_SORT_GRAN
							: g).invoke();
	}
}
