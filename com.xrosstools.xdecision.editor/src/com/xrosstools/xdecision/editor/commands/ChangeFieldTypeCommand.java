package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.FieldDefinition;

public class ChangeFieldTypeCommand extends Command{
    private FieldDefinition fieldDef;
    private DataType oldType;
    private DataType newType;
    
    public ChangeFieldTypeCommand(FieldDefinition fieldDef, DataType newType){
        this.fieldDef = fieldDef;
        this.oldType = fieldDef.getType();
        this.newType = newType;
    }
    
    public void execute() {
        redo();
    }

    public String getLabel() {
        return "Change field type";
    }

    public void redo() {
        fieldDef.setType(newType);
    }

    public void undo() {
        fieldDef.setType(oldType);
    }
}
