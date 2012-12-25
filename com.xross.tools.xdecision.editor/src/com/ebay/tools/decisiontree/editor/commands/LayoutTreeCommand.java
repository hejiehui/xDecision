package com.ebay.tools.decisiontree.editor.commands;

import org.eclipse.gef.commands.Command;

import com.ebay.tools.decisiontree.editor.model.DecisionTreeDiagram;

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

    public void redo() {
    	execute();
    }

    public void undo() {
    	diagram.setAlignment(oldAlignment);
    	diagram.setHorizantal(oldOrintation);
    }
}
