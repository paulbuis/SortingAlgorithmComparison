/*
 * Copyright (c) 1997, 2007, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
/* derived from OpenJDK 6 java.util.Arrays
 * by Paul Buis, 2015
 */
package edu.bsu.cs.sorting.javautil.generic;

import edu.bsu.cs.sorting.buis.generic.GenericHelpers;

public class EngineeredQuicksort {
	/**
	 * Sorts the specified array of ints into ascending numerical order. The
	 * sorting algorithm is a tuned quicksort, adapted from Jon L. Bentley and
	 * M. Douglas McIlroy's "Engineering a Sort Function", Software-Practice and
	 * Experience, Vol. 23(11) P. 1249-1265 (November 1993). This algorithm
	 * offers n*log(n) performance on many data sets that cause other quicksorts
	 * to degrade to quadratic performance.
	 *
	 * @param a
	 *            the array to be sorted
	 */
	public static <T extends Comparable<T>> void sort(T[] a) {
		sort1(a, 0, a.length);
	}

	/**
	 * Sorts the specified sub-array of integers into ascending order.
	 */
	private static <T extends Comparable<T>> void sort1(T x[], int off, int len) {
		// Insertion sort on smallest arrays
		if (len < 7) {
			for (int i = off; i < len + off; i++)
				for (int j = i; j > off
						&& GenericHelpers.greaterThan(x[j - 1], x[j]); j--)
					swap(x, j, j - 1);
			return;
		}

		// Choose a partition element, v
		int m = off + (len >> 1); // Small arrays, middle element
		if (len > 7) {
			int l = off;
			int n = off + len - 1;
			if (len > 40) { // Big arrays, pseudomedian of 9
				int s = len / 8;
				l = med3(x, l, l + s, l + 2 * s);
				m = med3(x, m - s, m, m + s);
				n = med3(x, n - 2 * s, n - s, n);
			}
			m = med3(x, l, m, n); // Mid-size, med of 3
		}
		T v = x[m];

		// Establish Invariant: v* (<v)* (>v)* v*
		int a = off, b = a, c = off + len - 1, d = c;
		while (true) {
			int comp;
			while (b <= c && (comp = x[b].compareTo(v)) <= 0) {
				if (comp == 0)
					swap(x, a++, b);
				b++;
			}
			while (c >= b && (comp = x[c].compareTo(v)) >= 0) {
				if (comp == 0)
					swap(x, c, d--);
				c--;
			}
			if (b > c)
				break;
			swap(x, b++, c--);
		}

		// Swap partition elements back to middle
		int s, n = off + len;
		s = Math.min(a - off, b - a);
		vecswap(x, off, b - s, s);
		s = Math.min(d - c, n - d - 1);
		vecswap(x, b, n - s, s);

		// Recursively sort non-partition-elements
		if ((s = b - a) > 1)
			sort1(x, off, s);
		if ((s = d - c) > 1)
			sort1(x, n - s, s);
	}

	/**
	 * Swaps x[a] with x[b].
	 */
	private static <T extends Comparable<T>> void swap(T x[], int a, int b) {
		T t = x[a];
		x[a] = x[b];
		x[b] = t;
	}

	/**
	 * Swaps x[a .. (a+n-1)] with x[b .. (b+n-1)].
	 */
	private static <T extends Comparable<T>> void vecswap(T x[], int a, int b,
			int n) {
		for (int i = 0; i < n; i++, a++, b++)
			swap(x, a, b);
	}

	/**
	 * Returns the index of the median of the three indexed integers.
	 */
	private static <T extends Comparable<T>> int med3(T x[], int a, int b, int c) {
		return (GenericHelpers.lessThan(x[a], x[b]) ? (GenericHelpers.lessThan(
				x[b], x[c]) ? b : GenericHelpers.lessThan(x[a], x[c]) ? c : a)
				: (GenericHelpers.greaterThan(x[b], x[c]) ? b : GenericHelpers
						.greaterThan(x[a], x[c]) ? c : a));
	}

}
