package edu.bsu.cs.sorting.testing;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TestResultTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private final TestResultMultiMap map;
	
	public TestResultTableModel(TestResultMultiMap map) {
		this.map = map;
	}

	@Override
	public String getColumnName(int column)
	{
		if (column == 0)
			return "Size";
		
		return super.getColumnName(column);
	}
	
	@Override
	public int getRowCount() {
		return map.keySet().size();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0)
			return Integer.class;
		
		return Double.class;
	}
	
	@Override
	public int getColumnCount() {
		int max = 0;
		for(Integer k: map) {
			int size = map.get(k).size();
			if (size > max)
				max = size;
		}
		return max + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Integer size = (Integer)map.keySet().toArray()[rowIndex];
		if (columnIndex == 0) {
			return size;
		}
		
		List<TestResult> list = map.get(size);
		if (list.size() < columnIndex)
			return null;
		TestResult result = list.get(columnIndex-1);
		return result.correct ? result.performance : Double.MAX_VALUE ;
	}

}
