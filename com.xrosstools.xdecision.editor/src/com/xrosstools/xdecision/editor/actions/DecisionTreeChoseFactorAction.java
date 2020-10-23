package com.xrosstools.xdecision.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xdecision.editor.commands.ChangeFactorCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;

public class DecisionTreeChoseFactorAction extends WorkbenchPartAction implements DecisionTreeActionConstants, DecisionTreeMessages{
    private DecisionTreeNode node;
    private int factorId;
    public DecisionTreeChoseFactorAction(IWorkbenchPart part, DecisionTreeNode node, String factorName, int factorId){
        super(part);
        setId(ID_PREFIX + CHOSE_FACTOR_VALUE + factorName);
        setText(factorName);
        this.factorId = factorId;
        setChecked(node.getFactorId() == factorId);
        this.node = node;
    }
    
    protected boolean calculateEnabled() {
        return true;
    }
    
    public void run() {
        execute(new ChangeFactorCommand(node, factorId));    
    }
}
