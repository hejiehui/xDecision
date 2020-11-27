package com.xrosstools.xdecision.editor.model;

public class DecisionTreePathEntry {
	private int nodeIndex;
	private int valueIndex;
	
	public DecisionTreePathEntry(int nodeIndex, int valueIndex){
		this.nodeIndex = nodeIndex;
		this.valueIndex = valueIndex;
	}

	public int getNodeIndex() {
		return nodeIndex;
	}
	public int getValueIndex() {
		return valueIndex;
	}
}
