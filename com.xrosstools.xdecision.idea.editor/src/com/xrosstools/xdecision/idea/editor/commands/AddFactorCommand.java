package com.xrosstools.xdecision.idea.editor.commands;

import com.xrosstools.gef.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;

import java.util.List;

public class AddFactorCommand extends Command {
	private List<DecisionTreeFactor> factors;
    private DecisionTreeNode node;
    private int oldFactorId;
    private int newFactorId;
    private DecisionTreeFactor newFactor;
    
    public AddFactorCommand(List<DecisionTreeFactor> factors, DecisionTreeNode node, DecisionTreeFactor newFactor){
    	this.factors = factors;
    	this.node = node;
    	oldFactorId = node.getFactorId();
    	this.newFactor = newFactor;
    }
    
    public void execute() {
    	newFactorId = factors.size();
    	factors.add(newFactor);
        node.setFactorId(newFactorId);
    }

    public String getLabel() {
        return "Change factor id";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	factors.remove(newFactorId);
    	node.setFactorId(oldFactorId);
    }
}
