package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.DataTypeEnum;
import com.xrosstools.xdecision.editor.model.FieldDefinition;
import com.xrosstools.xdecision.editor.model.UserDefinedType;

public class CreateFieldCommand extends InputTextCommand{
    private UserDefinedType parent;
    private FieldDefinition fieldDef;
    private DataType fieldType;
    
    public CreateFieldCommand(UserDefinedType parent, DataTypeEnum type, String customizedType){
        this.parent = parent;
        this.fieldType = new DataType(type, customizedType);
        fieldType.setType(type);
        fieldType.setCustomizedType(customizedType);
    }
    
    public void execute() {
        fieldDef = new FieldDefinition();
        fieldDef.setType(fieldType);
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
