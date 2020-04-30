package com.xrosstools.xdecision.idea.editor.commands;

import com.xrosstools.gef.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeFactor;

public class AddFactorValueCommand extends Command {
	private DecisionTreeFactor factor;
	private String[] oldValues;
	private String[] newValues;
	
	public AddFactorValueCommand(DecisionTreeFactor factor, String[] oldValues, String[] newValues){
		this.factor = factor;
		this.newValues = newValues;
		this.oldValues = oldValues;
	}
	
    public void execute() {
    	factor.setFactorValues(newValues);
    }

    public String getLabel() {
        return "Create factor value";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	factor.setFactorValues(oldValues);
    }
}
