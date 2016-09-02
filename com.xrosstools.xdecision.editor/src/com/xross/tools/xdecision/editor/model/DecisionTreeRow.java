package com.xross.tools.xdecision.editor.model;

import java.util.ArrayList;
import java.util.List;

public class DecisionTreeRow {
	private int maxChidrenNumber;
	private List<DecisionTreeNode> rowNodes = new ArrayList<DecisionTreeNode>();
	
	public int getMaxChidrenNumber() {
		return maxChidrenNumber;
	}
	public void setMaxChidrenNumber(int maxChidrenNumber) {
		this.maxChidrenNumber = maxChidrenNumber;
	}
	public List<DecisionTreeNode> getRowNodes() {
		return rowNodes;
	}
	public void setRowNodes(List<DecisionTreeNode> rowNodes) {
		this.rowNodes = rowNodes;
	}
}
