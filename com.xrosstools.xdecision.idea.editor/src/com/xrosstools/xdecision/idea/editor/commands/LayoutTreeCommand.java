package com.xrosstools.xdecision.idea.editor.commands;

import com.xrosstools.gef.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

public class LayoutTreeCommand extends Command {
	private DecisionTreeDiagram diagram;
	private boolean oldOrintation;
	private boolean orintation;
	private float oldAlignment;
	private float alignment;
	public LayoutTreeCommand(DecisionTreeDiagram diagram, boolean isHorizantal, float alignment){
		this.diagram = diagram;
		oldOrintation = diagram.isHorizantal();
		oldAlignment = diagram.getAlignment();
		this.alignment = alignment;
		orintation = isHorizantal;
	}
	
    public void execute() {
    	diagram.setAlignment(alignment); 
    	diagram.setHorizantal(orintation);
    }

	public String getLabel() {
		return "Layout tree";
	}

    public void redo() {
    	execute();
    }

    public void undo() {
    	diagram.setAlignment(oldAlignment);
    	diagram.setHorizantal(oldOrintation);
    }
}
