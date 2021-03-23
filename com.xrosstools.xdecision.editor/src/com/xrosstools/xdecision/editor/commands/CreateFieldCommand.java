package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.FieldDefinition;

public class CreateFieldCommand extends InputTextCommand{
    private DataType parent;
    private FieldDefinition fieldDef;
    private String fieldType;
    
    public CreateFieldCommand(DataType parent, String fieldType){
        this.parent = parent;
        this.fieldType = fieldType;
    }
    
    public void execute() {
        fieldDef = new FieldDefinition();
        fieldDef.setTypeName(fieldType);
        fieldDef.setName(getInputText());
        redo();
    }

    public String getLabel() {
        return "Create new field";
    }

    public void redo() {
        parent.getFields().add(fieldDef);
    }

    public void undo() {
        parent.getFields().remove(fieldDef);
    }
}
