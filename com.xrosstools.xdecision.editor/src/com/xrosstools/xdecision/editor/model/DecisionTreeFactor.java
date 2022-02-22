package com.xrosstools.xdecision.editor.model;

//TODO remove all values
public class DecisionTreeFactor extends NamedType {
	public DecisionTreeFactor(DecisionTreeDiagram diagram, String name) {
        super(diagram, name, NamedElementTypeEnum.FACTOR, DEFAULT_TYPE);
    }
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
