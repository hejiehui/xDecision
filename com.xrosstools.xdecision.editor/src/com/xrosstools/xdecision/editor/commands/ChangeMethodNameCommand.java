package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.MethodDefinition;

public class ChangeMethodNameCommand extends InputTextCommand {
    private MethodDefinition method;
    private String oldName;
    private String newName;
    
    public ChangeMethodNameCommand(MethodDefinition method){
        this.method = method;
        oldName = method.getName();
    }
    
    public void execute() {
        newName = getInputText();
        redo();
    }

    public String getLabel() {
        return "Change method name";
    }

    public void redo() {
        method.setName(newName);
    }

    public void undo() {
        method.setName(oldName);
    }
}