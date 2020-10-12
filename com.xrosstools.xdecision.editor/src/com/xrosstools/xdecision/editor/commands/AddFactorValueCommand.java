package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;

public class AddFactorValueCommand extends Command{
	private DecisionTreeFactor factor;
	private String[] oldValues;
	private String[] newValues;
	
	public AddFactorValueCommand(DecisionTreeFactor factor){
		this.factor = factor;
        this.oldValues = factor.getFactorValues();
	}
	
    public void execute() {
        InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), "Create new value for factor: ", "Value", "new factor value", null);
        if (dlg.open() != Window.OK)
            return;
        String newValue = dlg.getValue();
        int length = factor.getFactorValues().length;
        newValues = new String[length + 1];
        System.arraycopy(factor.getFactorValues(), 0, newValues, 0, length);
        newValues[length] = newValue;
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
