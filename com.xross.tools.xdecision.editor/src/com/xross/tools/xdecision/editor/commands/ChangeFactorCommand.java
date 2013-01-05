package com.xross.tools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xdecision.editor.model.DecisionTreeNode;

public class ChangeFactorCommand extends Command{
    private DecisionTreeNode node;
    private int oldFactorId;
    private int newFactorId;
    
    public ChangeFactorCommand(DecisionTreeNode node, int newFactorId){
    	this.node = node;
    	oldFactorId = node.getFactorId();
    	this.newFactorId = newFactorId;
    }
    
    public void execute() {
        node.setFactorId(newFactorId);
    }

    public String getLabel() {
        return "Change factor id";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	node.setFactorId(oldFactorId);
    }
}
