package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.NamedType;

public class ChangeElementTypeCommand extends Command{
    private NamedType typeNameSupported;
    private String newTypeName;
    private DataType oldType;
    private DataType newType;
    
    public ChangeElementTypeCommand(NamedType typeNameSupported, String newTypeName){
        this.typeNameSupported = typeNameSupported;
        this.newTypeName = newTypeName;
        this.oldType = typeNameSupported.getType();
    }
    
    public void execute() {
        newType = DataType.findDataType(newTypeName);
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
