package com.xross.tools.xdecision.editor.requests;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

import com.xross.tools.xdecision.editor.model.DecisionTreeDiagram;

public class DecisionTreeLayoutRequest extends Request {
	private DecisionTreeDiagram diagram;
	private boolean isHorizantal;
	private float alignment;
	
	public DecisionTreeLayoutRequest(DecisionTreeDiagram diagram, boolean isHorizantal, float alignment){
		super(RequestConstants.REQ_ALIGN);
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
