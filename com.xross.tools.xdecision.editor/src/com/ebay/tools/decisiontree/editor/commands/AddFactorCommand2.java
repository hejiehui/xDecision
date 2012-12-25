package com.ebay.tools.decisiontree.editor.commands;

import org.eclipse.gef.commands.Command;

import com.ebay.tools.decisiontree.editor.model.DecisionTreeDiagram;
import com.ebay.tools.decisiontree.utils.DecisionTreeFactor;

public class AddFactorCommand2 extends Command{
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
