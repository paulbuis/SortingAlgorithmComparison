package edu.bsu.cs.sorting.testing;

import edu.bsu.cs.utils.StopWatch;

import java.util.Random;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;

/**
 * This program demonstrates the merge sort algorithm by sorting an array that
 * is filled with random numbers.
 */
public class GenericSortTester<T extends Comparable<T>> {

	static private final int PUBLIC_STATIC = Modifier.PUBLIC | Modifier.STATIC;
	private final Method sortMethod;
	
	private static boolean rightMethod(Method m) {
		if (!m.getName().equals("sort"))
			return false;
		if (!((m.getModifiers() & PUBLIC_STATIC) == PUBLIC_STATIC))
			return false;
		if (m.getParameters().length != 1)
			return false;
		return true;
	}
	
	private static Method findRightMethod(String className) {
		try {
			Class<?> c = Class.forName(className);
			Method[] allMethods = c.getDeclaredMethods();
			for (int i=0; i<allMethods.length; i++) {
				Method m = allMethods[i];
				if (rightMethod(m)) {
					return m; 
				}
			}
			System.err.println("No sort(array) found in " + className);
			return null;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public GenericSortTester(String className) {
		sortMethod = findRightMethod(className);
	}
	
	// called in child thread which may be stopped
	// after a timeout by main thread, causing a
	// InvocationTargetException
	public boolean sort(T[] a) {
		Object[] args = {a};
		try {
			sortMethod.invoke(null, args);
			return true;
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			if (cause != null) {
				if (cause instanceof java.lang.ThreadDeath)
					return false;
				cause.printStackTrace();
			}
			System.err.println(e.toString());
		} catch (IllegalAccessException e) {
			System.err.println(e.toString());
		} catch (IllegalArgumentException e) {
			System.err.println(e.toString());
		} catch (StackOverflowError e) {
			System.err.println(e.toString());
		} catch (OutOfMemoryError e) {
			System.err.println(e.toString());
		}
		System.err.println("sort() failed");
		return false;	
	}
	
	public boolean correctnessTest(T[] a, T[] sorted) {
		if (sorted.length != a.length) {
			return false;
		}
		final int n = a.length;
		for (int i = 0; i < n; i++) {
			if (!a[i].equals(sorted[i])) {
				return false;
			}
		}

		return true;
	}
	

	/**
    Creates an array filled with random values.
    @param length the length of the array
    @param n the number of possible random values
    @return an array filled with length numbers between
    0 and n - 1
    */
	public static Integer[] randomIntegerArray(final int length, int n)
	{  
		Random generator = new Random(0);
		Integer[] a = new Integer[length]; 
		for (int i = 0; i < length; i++) {
			a[i] = generator.nextInt(n);
		}

		return a;
	}
	
	
	@SuppressWarnings("deprecation")
	public TestResult test(T[] input) {
		T[] sorted;
		double performance = Double.MAX_VALUE;
		boolean correct = false;
		TestResult result;
		try {
			sorted = java.util.Arrays.copyOf(input, input.length);
			java.util.Arrays.sort(sorted);
			StopWatch watch = new StopWatch();

			Thread runner = new Thread(new Runnable() {
					@Override
					public void run() {
						watch.start();
						sort(input); // returns success boolean, ignored!
						watch.stop();
					}}
			);
			
			runner.start();
			runner.join(100000); // 100 seconds = 100,000 milliseconds
			if (runner.isAlive()) {
				System.err.println("100 second timer expired!");
				runner.stop();
			}
			else {
				performance = watch.getElapsedTime();
				correct = correctnessTest(input, sorted);
			}
		}
		catch (InterruptedException e) {
			System.err.println(e.toString());
		}
		catch (StackOverflowError e) {
			System.err.println(e.toString());
		}
		catch (OutOfMemoryError e) {
			System.err.println(e.toString());
		}
		finally {
			result = new TestResult(correct, performance);
		}
		return result;
	}

	public static int pow10(int n) {
		int pow = 1;
		for (int i = 0; i < n; i++)
			pow *= 10;
		return pow;
	}

	public static void main(String[] args) {
		String className = "edu.bsu.cs.sorting.buis.generic.HeapSort";
		System.out.println(className);
		GenericSortTester<Integer> tester = new GenericSortTester<Integer>(className);
		TestResultMultiMap mmap = new TestResultMultiMap();
		
		for (int iPow = 3; iPow <= 7; iPow++) {
			int size = pow10(iPow);
			System.out.printf("%nArray Size= %,d%n", size);
			
			for (int repeatCount=0; repeatCount<5; repeatCount++) {
				Integer data[] = randomIntegerArray(size, Integer.MAX_VALUE);
				System.out.printf("Starting Test%n", size);
				TestResult result = tester.test(data);
				mmap.put(size, result);

				System.out.println("correct= " + result.correct);
				if (result.correct)
					System.out.printf("performance= %f seconds%n", result.performance);
				else
					break;
			}
		}
		
		TestResultTableModel tModel = new TestResultTableModel(mmap);
		JTable table = new JTable(tModel);
		JScrollPane pane = new JScrollPane(table);
		JFrame frame = new JFrame();
		frame.setTitle(className);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(pane);
		frame.pack();
		frame.setVisible(true);
		
	}
}
