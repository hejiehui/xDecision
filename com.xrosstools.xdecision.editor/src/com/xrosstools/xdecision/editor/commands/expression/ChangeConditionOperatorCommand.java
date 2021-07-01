package com.xrosstools.xdecision.editor.commands.expression;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.ConditionOperator;
import com.xrosstools.xdecision.editor.model.OperatorReference;

public class ChangeConditionOperatorCommand extends Command{
    private OperatorReference oprRef;
    private ConditionOperator  oldOpr;
    private ConditionOperator  newOpr;
    
    public ChangeConditionOperatorCommand(OperatorReference oprRef, ConditionOperator newOpr){
        this.oprRef = oprRef;
        this.oldOpr = oprRef.getOperator();
        this.newOpr = newOpr;
    }

    public void execute() {
        redo();
    }

    public String getLabel() {
        return "Change operator";
    }

    public void redo() {
        oprRef.setOperator(newOpr);
    }

    public void undo() {
        oprRef.setOperator(oldOpr);
    }
}
