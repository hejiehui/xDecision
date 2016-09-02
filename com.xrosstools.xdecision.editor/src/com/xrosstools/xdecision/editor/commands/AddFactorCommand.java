package com.xrosstools.xdecision.editor.commands;

import java.util.List;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;

public class AddFactorCommand extends Command{
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
