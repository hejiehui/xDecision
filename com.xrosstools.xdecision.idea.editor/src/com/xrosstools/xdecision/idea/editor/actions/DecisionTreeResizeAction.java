package com.xrosstools.xdecision.idea.editor.actions;

import com.xrosstools.gef.Command;
import com.xrosstools.gef.Action;
import com.xrosstools.xdecision.idea.editor.commands.ResizeNodeCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

public class DecisionTreeResizeAction extends Action implements DecisionTreeActionConstants {
	private boolean horizantal;
	private boolean nodeSize;
	private boolean increase;
	private DecisionTreeDiagram diagram;
	
	public DecisionTreeResizeAction(DecisionTreeDiagram diagram, boolean nodeSize, boolean horizantal, boolean increase){
		this.horizantal = horizantal;
		this.increase = increase;
		this.nodeSize = nodeSize;
		this.diagram = diagram;
	}

    @Override
    public Command createCommand() {
        return new ResizeNodeCommand(diagram, nodeSize, horizantal, increase);
    }
}
