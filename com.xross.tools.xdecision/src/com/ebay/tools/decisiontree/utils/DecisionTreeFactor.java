package com.ebay.tools.decisiontree.utils;

public class DecisionTreeFactor {
	private String factorName;
	private String[] factorValues;
	
	public String getFactorName() {
		return factorName;
	}
	public void setFactorName(String factorName) {
		this.factorName = factorName;
	}
	
	public int getFactorValueNum() {
		return factorValues.length;
	}
	
	public String[] getFactorValues() {
		return factorValues;
	}
	public void setFactorValues(String[] factorValues) {
		this.factorValues = factorValues;
	}
}
