package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.FactorType;
import com.xrosstools.xdecision.editor.model.FieldDefinition;

public class ChangeFieldTypeCommand extends Command{
    private FieldDefinition fieldDef;
    private FactorType oldType;
    private FactorType newType;
    private String oldCustomizedType;
    private String newCustomizedType;
    
    public ChangeFieldTypeCommand(FieldDefinition fieldDef, FactorType newType){
        this.fieldDef = fieldDef;
        oldCustomizedType = fieldDef.getCustomizedType();
        this.oldType = fieldDef.getType();
        this.newType = newType;
    }
    
    public ChangeFieldTypeCommand(FieldDefinition fieldDef, FactorType newType, String newCustomizedType){
        this(fieldDef, newType);
        this.newCustomizedType = newCustomizedType;
    }
    
    public void execute() {
        redo();
    }

    public String getLabel() {
        return "Change field type";
    }

    public void redo() {
        fieldDef.setType(newType);
        fieldDef.setCustomizedType(newCustomizedType);
    }

    public void undo() {
        fieldDef.setType(oldType);
        fieldDef.setCustomizedType(oldCustomizedType);
    }
}
