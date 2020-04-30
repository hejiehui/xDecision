package com.xrosstools.xdecision.idea.editor.commands;

import com.xrosstools.gef.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeFactor;

public class AddFactorCommand2 extends Command {
	private DecisionTreeDiagram diagram;
	private DecisionTreeFactor factor;

    public AddFactorCommand2(DecisionTreeDiagram diagram, DecisionTreeFactor factor){
    	this.diagram = diagram;
    	this.factor = factor;
    }
    
    public void execute() {
    	diagram.getFactors().add(factor);
    }

    public String getLabel() {
        return "Create new factor";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	diagram.getFactors().remove(factor);
    }
}
