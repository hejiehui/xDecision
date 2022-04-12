package com.xrosstools.xdecision.editor.commands.expression;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.ConditionOperator;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.PlaceholderExpression;

public class ChangeConditionOperatorCommand extends Command{
    private DecisionTreeNodeConnection conn;
    private ConditionOperator  oldOpr;
    private ExpressionDefinition oldExp;
    private ConditionOperator  newOpr;
    
    public ChangeConditionOperatorCommand(DecisionTreeNodeConnection conn, ConditionOperator newOpr){
        this.conn = conn;
        this.oldOpr = conn.getOperator();
        oldExp = conn.getExpression();
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
        if(!newOpr.requireParameter())
            conn.setExpression(PlaceholderExpression.EMPTY);
    }

    public void undo() {
        conn.setOperator(oldOpr);
        conn.setExpression(oldExp);
    }
}
