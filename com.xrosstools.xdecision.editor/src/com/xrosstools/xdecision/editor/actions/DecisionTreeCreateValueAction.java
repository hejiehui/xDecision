package com.xrosstools.xdecision.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xdecision.editor.commands.AddFactorValueCommand;
import com.xrosstools.xdecision.editor.commands.SetNewFactorValueCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;

public class DecisionTreeCreateValueAction extends WorkbenchPartAction implements DecisionTreeActionConstants, DecisionTreeMessages{
	private DecisionTreeFactor factor;
	private DecisionTreeNodeConnection nodeConn;
	
	//Just create
	public DecisionTreeCreateValueAction(IWorkbenchPart part, DecisionTreeFactor factor){
		super(part);
		setId(ID_PREFIX + CREATE_NEW_FACTOR_VALUE + factor.getFactorName());
		setText(factor.getFactorName());
		this.factor = factor;
	}
	
	//Create and set
	public DecisionTreeCreateValueAction(IWorkbenchPart part, DecisionTreeFactor factor, DecisionTreeNodeConnection nodeConn){
	    this(part, factor);
	    this.nodeConn = nodeConn;
	}

	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
	    if(nodeConn == null)
	        execute(createCommand(factor));
	    else
	        execute(createAndSetValueCommand(factor, nodeConn));
	}
	
	public static Command createCommand(DecisionTreeFactor factor) {
	    InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), "Create new value for factor: ", "Value", "new factor value", null);
	    if (dlg.open() != Window.OK)
	        return null;
        String newValue = dlg.getValue();
        int length = factor.getFactorValues().length;
        String[] newValues = new String[length + 1];
        System.arraycopy(factor.getFactorValues(), 0, newValues, 0, length);
        newValues[length] = newValue;
        return new AddFactorValueCommand(factor, factor.getFactorValues(), newValues);
    }

	public static Command createAndSetValueCommand(DecisionTreeFactor factor, DecisionTreeNodeConnection nodeConn) {
        Command createValue = createCommand(factor);
        if(createValue == null)
            return null;
        
        CommandChain cc = new CommandChain();
        cc.add(createValue);
        cc.add(new SetNewFactorValueCommand(nodeConn, factor.getFactorValueNum()));
        return cc;
	}
}
