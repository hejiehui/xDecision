package com.xrosstools.xdecision.idea.editor.io;


import com.xrosstools.xdecision.idea.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreePath;

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
