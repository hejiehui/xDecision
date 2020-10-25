package com.xrosstools.xdecision.idea.editor.actions;

import com.xrosstools.gef.Action;
import com.xrosstools.gef.Command;
import com.xrosstools.xdecision.idea.editor.commands.SetNewFactorValueCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;

public class DecisionTreeChoseValueAction extends Action implements DecisionTreeActionConstants {
    private DecisionTreeNodeConnection conn;
    private int factorValueId;
    public DecisionTreeChoseValueAction(DecisionTreeNodeConnection conn, String factorValue, int factorValueId){
        setText(factorValue);
        this.factorValueId = factorValueId;
        setChecked(conn.getValueId() == factorValueId);
        this.conn = conn;
    }

    public Command createCommand() {
        return new SetNewFactorValueCommand(conn, factorValueId);
    }
}
