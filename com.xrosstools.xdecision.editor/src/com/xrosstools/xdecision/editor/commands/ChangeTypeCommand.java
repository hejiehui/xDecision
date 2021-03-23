package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.FieldDefinition;

public class ChangeTypeCommand extends Command{
    private FieldDefinition typeNameSupported;
    private String oldType;
    private String newType;
    
    public ChangeTypeCommand(FieldDefinition typeNameSupported, String newType){
        this.typeNameSupported = typeNameSupported;
        this.oldType = typeNameSupported.getTypeName();
        this.newType = newType;
    }
    
    public void execute() {
        redo();
    }

    public String getLabel() {
        return "Change Data Type";
    }

    public void redo() {
        typeNameSupported.setTypeName(newType);
    }

    public void undo() {
        typeNameSupported.setTypeName(oldType);
    }
}
