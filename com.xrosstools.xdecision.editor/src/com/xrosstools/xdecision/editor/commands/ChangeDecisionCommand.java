package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DecisionTreeDecision;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;

public class ChangeDecisionCommand extends Command{
    private DecisionTreeNode node;
    private DecisionTreeDecision oldDecision;
    private DecisionTreeDecision newDecision;
    
    public ChangeDecisionCommand(DecisionTreeNode node, DecisionTreeDecision newDecision){
        this.node = node;
        oldDecision = node.getDecision();
        this.newDecision = newDecision;
    }
    
    public void execute() {
        node.setDecision(newDecision);
    }

    public String getLabel() {
        return "Change decision id";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        node.setDecision(oldDecision);
    }
}
