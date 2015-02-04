package com.xross.tools.xdecision;

public class XDecisionPathEntry {
	private String factorName;
	private Object value;

	public XDecisionPathEntry(String factorName, Object value) {
		this.factorName = factorName;
		this.value = value;
	}
	
	public String getFactorName() {
		return factorName;
	}
	
	public Object getValue() {
		return value;
	}
}
