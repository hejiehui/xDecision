package com.xrosstools.xdecision.idea.editor.requests;

import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

public class DecisionTreeLayoutRequest {
	private DecisionTreeDiagram diagram;
	private boolean isHorizantal;
	private float alignment;
	
	public DecisionTreeLayoutRequest(DecisionTreeDiagram diagram, boolean isHorizantal, float alignment){
		this.diagram = diagram;
		this.alignment = alignment;
		this.isHorizantal = isHorizantal;
	}
	
	public boolean isHorizantal() {
		return isHorizantal;
	}

	public float getAlignment() {
		return alignment;
	}

	public DecisionTreeDiagram getDiagram() {
		return diagram;
	}
}
