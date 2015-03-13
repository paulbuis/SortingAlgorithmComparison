package edu.bsu.cs.sorting.testing;

public class TestResult {
	public final double performance;
	public final boolean correct;
	
	public TestResult(boolean correct, double performance) {
		this.correct = correct;
		this.performance = correct ? performance : Double.MAX_VALUE;
	}
	
	public TestResult combine(TestResult otherResult) {
		return new TestResult(correct && otherResult.correct,
				Math.max(performance, otherResult.performance));
	}

}
