package com.xrosstools.xdecision.idea.editor.commands;

import com.xrosstools.gef.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

public class AddDecisionCommand extends Command {
    private DecisionTreeDiagram diagram;
    private String decision;
    
    public AddDecisionCommand(DecisionTreeDiagram diagram, String decision){
    	this.diagram = diagram;
    	this.decision = decision;
    }
    
    public void execute() {
    	diagram.getDecisions().add(decision);
    }

    public String getLabel() {
        return "Create new decision";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	diagram.getDecisions().remove(diagram.getDecisions().size() - 1);
    }
}
