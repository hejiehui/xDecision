package com.xrosstools.xdecision.idea.editor.requests;

import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

public class DecisionTreeResizeRequest {
	private DecisionTreeDiagram diagram;
	private boolean horizantal;
	private boolean nodeSize;
	private boolean increase;
	
	public DecisionTreeResizeRequest(DecisionTreeDiagram diagram, boolean nodeSize, boolean horizantal, boolean increase){
		this.diagram = diagram;
		this.horizantal = horizantal;
		this.nodeSize = nodeSize;
		this.increase = increase;
	}

	public DecisionTreeDiagram getDiagram() {
		return diagram;
	}

	public boolean isHorizantal() {
		return horizantal;
	}

	public boolean isIncrease() {
		return increase;
	}

	public boolean isNodeSize() {
		return nodeSize;
	}
}
