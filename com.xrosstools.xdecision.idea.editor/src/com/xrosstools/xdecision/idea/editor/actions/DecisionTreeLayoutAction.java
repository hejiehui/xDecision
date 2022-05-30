package com.xrosstools.xdecision.idea.editor.actions;

import com.xrosstools.gef.commands.Command;
import com.xrosstools.gef.actions.Action;
import com.xrosstools.xdecision.idea.editor.commands.LayoutTreeCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

import java.beans.PropertyChangeListener;

public class DecisionTreeLayoutAction extends Action implements DecisionTreeActionConstants {
	private boolean horizantal;
	private float alignment;
	private DecisionTreeDiagram diagram;

	public DecisionTreeLayoutAction(DecisionTreeDiagram diagram, boolean horizantal, float alignment, PropertyChangeListener listener){
		this.alignment = alignment;
		this.horizantal = horizantal;
		this.diagram = diagram;
		setListener(listener);
	}

	@Override
	public Command createCommand() {
		return new LayoutTreeCommand(diagram, horizantal, alignment);
	}
}