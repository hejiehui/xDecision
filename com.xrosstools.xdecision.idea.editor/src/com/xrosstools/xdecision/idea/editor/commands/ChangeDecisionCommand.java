package com.xrosstools.xdecision.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDecision;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;

public class ChangeDecisionCommand extends Command {
    private boolean useLastCreated;
    private DecisionTreeNode node;
    private DecisionTreeDecision oldDecision;
    private DecisionTreeDecision newDecision;

    public ChangeDecisionCommand(DecisionTreeNode node){
        this(node, null);
        useLastCreated = true;
    }

    public ChangeDecisionCommand(DecisionTreeNode node, DecisionTreeDecision decision){
        this.node = node;
        oldDecision = node.getDecision();
        this.newDecision = decision;
    }

    public void execute() {
        if(useLastCreated)
            newDecision = node.getDecisionTreeManager().getDecisions().get(node.getDecisionTreeManager().getDecisions().size() -1);
        redo();
    }

    public String getLabel() {
        return "Change decision id";
    }

    public void redo() {
        node.setDecision(newDecision);
    }

    public void undo() {
        node.setDecision(oldDecision);
    }
}
