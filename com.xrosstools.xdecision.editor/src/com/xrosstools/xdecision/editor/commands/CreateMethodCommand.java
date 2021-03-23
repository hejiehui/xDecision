package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.MethodDefinition;

public class CreateMethodCommand extends InputTextCommand{
    private DataType parent;
    private MethodDefinition methodDef;
    private String returnType;
    
    public CreateMethodCommand(DataType parent, String type){
        this.parent = parent;
        this.returnType = type;
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
