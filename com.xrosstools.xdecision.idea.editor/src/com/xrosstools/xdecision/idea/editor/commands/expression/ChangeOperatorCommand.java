package com.xrosstools.xdecision.idea.editor.commands.expression;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xdecision.idea.editor.model.expression.OperatorEnum;
import com.xrosstools.xdecision.idea.editor.model.expression.OperatorExpression;

public class ChangeOperatorCommand extends Command {
    private OperatorExpression op;
    private OperatorEnum oldType;
    private OperatorEnum newType;
    
    public ChangeOperatorCommand(OperatorExpression op, OperatorEnum newType){
        this.op = op;
        this.oldType = op.getOperator();
        this.newType = newType;
    }

    public void execute() {
        redo();
    }

    public String getLabel() {
        return "Change operator";
    }

    public void redo() {
        op.setOperator(newType);
    }

    public void undo() {
        op.setOperator(oldType);
    }
}
