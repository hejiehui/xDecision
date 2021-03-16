package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;

public class ChangeFactorTypeCommand extends Command{
    private DecisionTreeFactor factor;
    private DataType oldType;
    private DataType newType;
    private String oldCustomizedType;
    private String newCustomizedType;
    
    public ChangeFactorTypeCommand(DecisionTreeFactor factor, DataType newType){
        this.factor = factor;
        this.oldType = factor.getType();
        this.newType = newType;
    }
    
    public void execute() {
        redo();
    }

    public String getLabel() {
        return "Change factor type";
    }

    public void redo() {
        factor.setType(newType);
    }

    public void undo() {
        factor.setType(oldType);
    }
}
