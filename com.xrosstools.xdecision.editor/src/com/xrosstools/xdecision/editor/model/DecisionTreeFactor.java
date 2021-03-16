package com.xrosstools.xdecision.editor.model;

public class DecisionTreeFactor extends FieldDefinition {
	private String[] factorValues = new String[0];
	
    public String getFactorName() {
		return getName();
	}
	public void setFactorName(String factorName) {
		setName(factorName);
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
