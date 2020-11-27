package com.xrosstools.xdecision.editor.model;

public class DecisionTreeFactor {
	private String factorName;
	private String[] factorValues = new String[0];
	private String label;
    private FactorType type;
    private String customizedType;
	
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
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public FactorType getType() {
        return type;
    }
    public void setType(FactorType type) {
        this.type = type;
    }
    public String getCustomizedType() {
        return customizedType;
    }
    public void setCustomizedType(String customizedType) {
        this.customizedType= customizedType;
    }
}
