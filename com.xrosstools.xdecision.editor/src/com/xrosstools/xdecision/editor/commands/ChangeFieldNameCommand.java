package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.FieldDefinition;

public class ChangeFieldNameCommand extends InputTextCommand {
    private  FieldDefinition field;
    private String oldName;
    private String newName;
    
    public ChangeFieldNameCommand(FieldDefinition field){
        this.field = field;
        oldName = field.getName();
    }
    
    public void execute() {
        newName = getInputText();
        redo();
    }

    public String getLabel() {
        return "Change field name";
    }

    public void redo() {
        field.setName(newName);
    }

    public void undo() {
        field.setName(oldName);
    }
}