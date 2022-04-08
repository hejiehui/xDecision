package com.xrosstools.xdecision.editor.model;

import com.xrosstools.xdecision.editor.model.definition.NamedElementTypeEnum;
import com.xrosstools.xdecision.editor.model.definition.NamedType;

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
