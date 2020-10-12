package com.xrosstools.xdecision.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xdecision.editor.commands.AddFactorValueCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;

public class DecisionTreeCreateValueAction extends WorkbenchPartAction implements DecisionTreeActionConstants, DecisionTreeMessages{
	private DecisionTreeFactor factor;
	public DecisionTreeCreateValueAction(IWorkbenchPart part, DecisionTreeFactor factor){
		super(part);
		setId(ID_PREFIX + CREATE_NEW_FACTOR_VALUE + factor.getFactorName());
		setText(factor.getFactorName());
		this.factor = factor;
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
        execute(new AddFactorValueCommand(factor));
	}
	
//	public static AddFactorValueCommand createFactorValue(DecisionTreeFactor factor) {
//	    InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), "Create new value for factor: ", "Value", "new factor value", null);
//	    if (dlg.open() != Window.OK)
//	        return null;
//        String newValue = dlg.getValue();
//        int length = factor.getFactorValues().length;
//        String[] newValues = new String[length + 1];
//        System.arraycopy(factor.getFactorValues(), 0, newValues, 0, length);
//        newValues[length] = newValue;
//        return new AddFactorValueCommand(factor, factor.getFactorValues(), newValues);
//    }

}
