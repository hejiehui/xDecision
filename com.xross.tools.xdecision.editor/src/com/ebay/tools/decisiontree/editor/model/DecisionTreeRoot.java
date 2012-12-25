package com.ebay.tools.decisiontree.editor.model;

import java.util.ArrayList;
import java.util.List;

public class DecisionTreeRoot {
	private DecisionTreeNode rootNode;
	private List<DecisionTreeRow> rows = new ArrayList<DecisionTreeRow>();
	private int width;
	
	public DecisionTreeRoot(DecisionTreeNode rootNode){
		this.rootNode = rootNode;
	}
	
	public DecisionTreeNode getRootNode() {
		return rootNode;
	}
	public List<DecisionTreeRow> getRows() {
		return rows;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
}
