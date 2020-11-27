package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DecisionTreeNode;

public class ChangeFactorFieldCommand extends Command{
    private DecisionTreeNode node;
    private String oldFieldName;
    private String newFieldName;
    
    public ChangeFactorFieldCommand(DecisionTreeNode node, String newFieldName){
        this.node = node;
        this.oldFieldName = node.getFactorField();
        this.newFieldName = newFieldName;
    }

    public void execute() {
        redo();
    }

    public String getLabel() {
        return "Change factor field";
    }

    public void redo() {
        node.setFactorField(newFieldName);
    }

    public void undo() {
        node.setFactorField(oldFieldName);
    }
}
