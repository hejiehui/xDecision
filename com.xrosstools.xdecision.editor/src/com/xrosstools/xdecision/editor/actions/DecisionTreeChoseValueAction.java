package com.xrosstools.xdecision.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xdecision.editor.commands.SetNewFactorValueCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;

public class DecisionTreeChoseValueAction extends WorkbenchPartAction implements DecisionTreeActionConstants, DecisionTreeMessages{
    private DecisionTreeNodeConnection conn;
    private int factorValueId;
    public DecisionTreeChoseValueAction(IWorkbenchPart part, DecisionTreeNodeConnection conn, String factorValue, int factorValueId){
        super(part);
        setId(ID_PREFIX + CHOSE_FACTOR_VALUE + factorValue);
        setText(factorValue);
        this.factorValueId = factorValueId;
        setChecked(conn.getValueId() == factorValueId);
        this.conn = conn;
    }
    
    protected boolean calculateEnabled() {
        return true;
    }
    
    public void run() {
        execute(new SetNewFactorValueCommand(conn, factorValueId));
    }
}
