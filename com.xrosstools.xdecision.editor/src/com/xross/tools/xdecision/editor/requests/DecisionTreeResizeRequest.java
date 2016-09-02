package com.xross.tools.xdecision.editor.requests;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

import com.xross.tools.xdecision.editor.model.DecisionTreeDiagram;

public class DecisionTreeResizeRequest  extends Request {
	private DecisionTreeDiagram diagram;
	private boolean horizantal;
	private boolean nodeSize;
	private boolean increase;
	
	public DecisionTreeResizeRequest(DecisionTreeDiagram diagram, boolean nodeSize, boolean horizantal, boolean increase){
		super(RequestConstants.REQ_RESIZE);
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
