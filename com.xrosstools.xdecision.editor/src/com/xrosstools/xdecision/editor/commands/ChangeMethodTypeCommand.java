package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.MethodDefinition;

public class ChangeMethodTypeCommand extends Command{
    private MethodDefinition methoDef;
    private DataType oldType;
    private DataType newType;
    
    public ChangeMethodTypeCommand(MethodDefinition methodDef, DataType newType){
        this.methoDef = methodDef;
        this.oldType = methodDef.getType();
        this.newType = newType;
    }
    
    public void execute() {
        redo();
    }

    public String getLabel() {
        return "Change method type";
    }

    public void redo() {
        methoDef.setType(newType);
    }

    public void undo() {
        methoDef.setType(oldType);
    }
}
