package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;

public class AddFactorValueCommand extends Command{
	private DecisionTreeFactor factor;
	private String[] oldValues;
	private String[] newValues;
	
	public AddFactorValueCommand(DecisionTreeFactor factor, String[] oldValues, String[] newValues){
		this.factor = factor;
        this.oldValues = oldValues;
        this.newValues = newValues;
	}
	
    public void execute() {
        redo();
    }

    public String getLabel() {
        return "Create factor value";
    }

    public void redo() {
        if(newValues != null)
            factor.setFactorValues(newValues);
    }

    public void undo() {
    	factor.setFactorValues(oldValues);
    }
}
