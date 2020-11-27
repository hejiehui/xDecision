package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.FactorType;
import com.xrosstools.xdecision.editor.model.FieldDefinition;
import com.xrosstools.xdecision.editor.model.UserDefinedType;

public class CreateUserDefineidTypeFieldCommand extends InputTextCommand{
    private UserDefinedType parent;
    private FieldDefinition fieldDef;
    private FactorType type;
    private String customizedType;
    
    public CreateUserDefineidTypeFieldCommand(UserDefinedType parent, FactorType type, String customizedType){
        this.parent = parent;
        this.type = type;
        this.customizedType = customizedType;
    }
    
    public void execute() {
        fieldDef = new FieldDefinition();
        fieldDef.setName(getInputText());
        fieldDef.setType(type);
        fieldDef.setCustomizedType(customizedType);
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
