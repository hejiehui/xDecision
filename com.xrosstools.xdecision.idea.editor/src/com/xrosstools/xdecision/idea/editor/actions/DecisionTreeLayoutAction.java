package com.xrosstools.xdecision.idea.editor.actions;

import com.xrosstools.gef.Command;
import com.xrosstools.gef.Action;
import com.xrosstools.xdecision.idea.editor.commands.LayoutTreeCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

public class DecisionTreeLayoutAction extends Action implements DecisionTreeActionConstants {
	private boolean horizantal;
	private float alignment;
	private DecisionTreeDiagram diagram;
	
	public DecisionTreeLayoutAction(DecisionTreeDiagram diagram, boolean horizantal, float alignment){
		this.alignment = alignment;
		this.horizantal = horizantal;
		this.diagram = diagram;
	}

	@Override
	public Command createCommand() {
		return new LayoutTreeCommand(diagram, horizantal, alignment);
	}
}