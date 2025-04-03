package com.xrosstools.xdecision.idea.editor.actions;

import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xdecision.idea.editor.commands.LayoutTreeCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

public class DecisionTreeLayoutAction extends Action implements DecisionTreeActionConstants {
	private boolean horizantal;
	private float alignment;
	private DecisionTreeDiagram diagram;

	public DecisionTreeLayoutAction(boolean horizantal, float alignment){
		this.alignment = alignment;
		this.horizantal = horizantal;
	}

	public void setDiagram(DecisionTreeDiagram diagram) {
		this.diagram = diagram;
	}

	@Override
	public Command createCommand() {
		return new LayoutTreeCommand(diagram, horizantal, alignment);
	}
}