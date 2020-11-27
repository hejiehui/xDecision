package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.FactorType;

public class ChangeFactorTypeCommand extends Command{
    private DecisionTreeFactor factor;
    private FactorType oldType;
    private FactorType newType;
    private String oldCustomizedType;
    private String newCustomizedType;
    
    public ChangeFactorTypeCommand(DecisionTreeFactor factor, FactorType newType){
        this.factor = factor;
        oldCustomizedType = factor.getCustomizedType();
        this.oldType = factor.getType();
        this.newType = newType;
    }
    
    public ChangeFactorTypeCommand(DecisionTreeFactor factor, FactorType newType, String newCustomizedType){
        this(factor, newType);
        this.newCustomizedType = newCustomizedType;
    }

    public void execute() {
        redo();
    }

    public String getLabel() {
        return "Change factor type";
    }

    public void redo() {
        factor.setType(newType);
        factor.setCustomizedType(newCustomizedType);
    }

    public void undo() {
        factor.setType(oldType);
        factor.setCustomizedType(oldCustomizedType);
    }
}
