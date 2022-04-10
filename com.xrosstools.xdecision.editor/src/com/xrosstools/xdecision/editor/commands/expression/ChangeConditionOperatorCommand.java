package com.xrosstools.xdecision.editor.commands.expression;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.ConditionOperator;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;

public class ChangeConditionOperatorCommand extends Command{
    private DecisionTreeNodeConnection conn;
    private ConditionOperator  oldOpr;
    private ConditionOperator  newOpr;
    
    public ChangeConditionOperatorCommand(DecisionTreeNodeConnection conn, ConditionOperator newOpr){
        this.conn = conn;
        this.oldOpr = conn.getOperator();
        this.newOpr = newOpr;
    }

    public void execute() {
        redo();
    }

    public String getLabel() {
        return "Change operator";
    }

    public void redo() {
        conn.setOperator(newOpr);
    }

    public void undo() {
        conn.setOperator(oldOpr);
    }
}
