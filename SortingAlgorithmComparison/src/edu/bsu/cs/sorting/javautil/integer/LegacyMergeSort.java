/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
 * Copyright 2009 Google Inc.  All Rights Reserved.
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

/* Derived from OpenJDK jdk8-b123 java.util.Arrays.java
 * by Paul Buis, 2015 
 */
package edu.bsu.cs.sorting.javautil.integer;



public class LegacyMergeSort {

	/**
	 * Prevents instantiation.
	 */
	private LegacyMergeSort() {
	}
	
    public static void sort(int[] a) {
        int[] aux = a.clone();
        sort(aux, a, 0, a.length);
    }
    
    /**
     * Tuning parameter: list size at or below which insertion sort will be
     * used in preference to mergesort.
     * To be removed in a future release.
     */
    private static final int INSERTIONSORT_THRESHOLD = 7;
	
    /**
     * Src is the source array that starts at index 0
     * Dest is the (possibly larger) array destination with a possible offset
     * low is the index in dest to start sorting
     * high is the end index in dest to end sorting
     * off is the offset to generate corresponding low, high in src
     * To be removed in a future release.
     */
    public static  void sort(int[] src, int[] dest,
                                  int low,
                                  int high) {
        int length = high - low;

        // Insertion sort on smallest arrays
        if (length < INSERTIONSORT_THRESHOLD) {
            for (int i=low; i<high; i++)
                for (int j=i; j>low &&
                		(dest[j-1] > dest[j]); j--)
                    swap(dest, j, j-1);
            return;
        }

        // Recursively sort halves of dest into src
        int mid = (low + high) >>> 1; // right shift to divide by 2
        sort(dest, src, low, mid);
        sort(dest, src, mid, high);
        merge(src, dest, low, mid, high);
    }
    
    private static void merge(int[] src, int[] dest, int low, int mid, int high) {
        // If list is already sorted, just copy from src to dest.  This is an
        // optimization that results in faster sorts for nearly ordered lists.
        int length = high - low;
        
        if (src[mid-1] <= src[mid]) {
            System.arraycopy(src, low, dest, low, length);
            return;
        }

        // Merge sorted halves (now in src) into dest
        for(int i = low, p = low, q = mid; i < high; i++) {
            if (q >= high || p < mid && (src[p] <= src[q]))
                dest[i] = src[p++];
            else
                dest[i] = src[q++];
        }
    }
    
    /**
     * Swaps x[a] with x[b].
     */
    private static void swap(int[] x, int a, int b) {
        int t = x[a];
        x[a] = x[b];
        x[b] = t;
    }

}
