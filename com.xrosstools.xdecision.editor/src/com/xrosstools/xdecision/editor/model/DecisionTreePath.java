package com.xrosstools.xdecision.editor.model;

public class DecisionTreePath {
	private DecisionTreePathEntry[] pathEntries;
	private int decisionIndex;
	
	public DecisionTreePath(DecisionTreePathEntry[] pathEntries, int decisionIndex){
		this.pathEntries = pathEntries;
		this.decisionIndex = decisionIndex;
	}

	public DecisionTreePathEntry[] getPathEntries() {
		return pathEntries;
	}

	public int getDecisionIndex() {
		return decisionIndex;
	}
}
