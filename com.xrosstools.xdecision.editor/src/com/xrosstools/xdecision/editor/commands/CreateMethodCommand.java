package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.DataTypeEnum;
import com.xrosstools.xdecision.editor.model.MethodDefinition;
import com.xrosstools.xdecision.editor.model.UserDefinedType;

public class CreateMethodCommand extends InputTextCommand{
    private UserDefinedType parent;
    private MethodDefinition methodDef;
    private DataType returnType;
    
    public CreateMethodCommand(UserDefinedType parent, DataTypeEnum type, String customizedType){
        this.parent = parent;
        this.returnType = new DataType(type, customizedType);
        returnType.setType(type);
        returnType.setCustomizedType(customizedType);
    }
    
    public void execute() {
        String name = getInputText();
        methodDef = new MethodDefinition(name, name, returnType, "");
        redo();
    }

    public String getLabel() {
        return "Create new field";
    }

    public void redo() {
        parent.getMethods().add(methodDef);
    }

    public void undo() {
        parent.getMethods().remove(methodDef);
    }
}
