package com.xross.tools.xdecision.utils;

public class DecisionTreePathEntry {
	private int factorIndex;
	private int valueIndex;
	
	public DecisionTreePathEntry(int factorIndex, int valueIndex){
		this.factorIndex = factorIndex;
		this.valueIndex = valueIndex;
	}

	public int getFactorIndex() {
		return factorIndex;
	}
	public int getValueIndex() {
		return valueIndex;
	}
}
