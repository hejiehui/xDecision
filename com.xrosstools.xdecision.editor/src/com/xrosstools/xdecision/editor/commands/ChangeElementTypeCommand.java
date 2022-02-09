package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.NamedType;

public class ChangeElementTypeCommand extends Command{
    private NamedType typeNameSupported;
    private DataType oldType;
    private DataType newType;
    
    public ChangeElementTypeCommand(NamedType typeNameSupported, DataType newType){
        this.typeNameSupported = typeNameSupported;
        this.oldType = typeNameSupported.getType();
        this.newType = newType;
    }
    
    public void execute() {
        redo();
    }

    public String getLabel() {
        return "Change Data Type";
    }

    public void redo() {
        typeNameSupported.setType(newType);
    }

    public void undo() {
        typeNameSupported.setType(oldType);
    }
}
