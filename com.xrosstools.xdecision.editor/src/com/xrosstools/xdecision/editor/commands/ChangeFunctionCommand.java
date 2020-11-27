package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DecisionTreeNode;

public class ChangeFunctionCommand extends Command{
    private DecisionTreeNode node;
    private String oldFunctionName;
    private String newFunctionName;
    
    public ChangeFunctionCommand(DecisionTreeNode node, String newFunctionName){
        this.node = node;
        oldFunctionName = node.getFunctionName();
        this.newFunctionName = newFunctionName;
    }
    
    public void execute() {
        redo();
    }

    public String getLabel() {
        return "Change function";
    }

    public void redo() {
        node.setFunctionName(newFunctionName);
    }

    public void undo() {
        node.setFunctionName(oldFunctionName);
    }
}
