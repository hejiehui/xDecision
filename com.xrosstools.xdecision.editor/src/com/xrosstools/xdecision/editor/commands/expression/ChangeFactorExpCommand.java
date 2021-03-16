package com.xrosstools.xdecision.editor.commands.expression;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.expression.FactorExpression;

public class ChangeFactorExpCommand extends Command{
    private FactorExpression exp;
    private int oldFactorId;
    private int newFactorId;
    
    public ChangeFactorExpCommand(FactorExpression exp, int newFactorId){
        this.exp = exp;
        this.oldFactorId = exp.getFactorId();
        this.newFactorId = newFactorId;
    }
    
    public void execute() {
        exp.setFactorId(newFactorId);
    }

    public String getLabel() {
        return "Change factor id";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        exp.setFactorId(oldFactorId);
    }
}
