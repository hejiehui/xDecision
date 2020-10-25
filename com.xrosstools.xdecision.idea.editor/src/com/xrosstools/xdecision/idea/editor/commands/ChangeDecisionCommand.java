package com.xrosstools.xdecision.idea.editor.commands;

import com.xrosstools.gef.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;

public class ChangeDecisionCommand extends Command {
    private DecisionTreeNode node;
    private int oldDecisionId;
    private int newDecisionId;
    
    public ChangeDecisionCommand(DecisionTreeNode node, int newDecisionId){
        this.node = node;
        oldDecisionId = node.getDecisionId();
        this.newDecisionId = newDecisionId;
    }
    
    public void execute() {
        node.setDecisionId(newDecisionId);
    }

    public String getLabel() {
        return "Change decision id";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        node.setDecisionId(oldDecisionId);
    }
}
