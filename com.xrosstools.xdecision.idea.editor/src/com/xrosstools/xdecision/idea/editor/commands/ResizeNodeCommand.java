package com.xrosstools.xdecision.idea.editor.commands;

import com.xrosstools.gef.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

public class ResizeNodeCommand extends Command {
	private DecisionTreeDiagram diagram;

	private boolean horizantal;
	private boolean nodeSize;
	private int oldSize;
	private int size;

	public ResizeNodeCommand(DecisionTreeDiagram diagram, boolean nodeSize, boolean horizantal, boolean increase){
		this.diagram = diagram;

		this.nodeSize = nodeSize;
		this.horizantal = horizantal;
		if(nodeSize)
			oldSize = horizantal ? diagram.getNodeWidth() : diagram.getNodeHeight();
		else
			oldSize = horizantal ? diagram.getHorizantalSpace() : diagram.getVerticalSpace();
			
		size = oldSize + (increase ? 10 : -10);
		if(size <= 0)
			size = 10;
	}
	
    public void execute() {
    	if(nodeSize)
	    	if(horizantal)
				diagram.setNodeWidth(size);
			else
				diagram.setNodeHeight(size);
    	else
	    	if(horizantal)
				diagram.setHorizantalSpace(size);
			else
				diagram.setVerticalSpace(size);    		
    }

	@Override
	public String getLabel() {
		return "Resize node";
	}

	public void redo() {
    	execute();
    }

    public void undo() {
    	if(nodeSize)
	    	if(horizantal)
				diagram.setNodeWidth(oldSize);
			else
				diagram.setNodeHeight(oldSize);
    	else
	    	if(horizantal)
				diagram.setHorizantalSpace(oldSize);
			else
				diagram.setVerticalSpace(oldSize);    		
    }
}
