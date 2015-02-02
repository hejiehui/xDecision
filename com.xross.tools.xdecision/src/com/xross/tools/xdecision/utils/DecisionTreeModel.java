package com.xross.tools.xdecision.utils;

public class DecisionTreeModel {
	private String comments;
	private String[] decisions;
	private DecisionTreeFactor[] factors;
	private DecisionTreePath[] pathes;
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String[] getDecisions() {
		return decisions;
	}
	public void setDecisions(String[] decisions) {
		this.decisions = decisions;
	}
	public DecisionTreeFactor[] getFactors() {
		return factors;
	}
	public void setFactors(DecisionTreeFactor[] factors) {
		this.factors = factors;
	}
	public DecisionTreePath[] getPathes() {
		return pathes;
	}
	public void setPathes(DecisionTreePath[] pathes) {
		this.pathes = pathes;
	}
}
