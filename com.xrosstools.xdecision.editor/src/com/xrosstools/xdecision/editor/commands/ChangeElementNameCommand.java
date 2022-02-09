package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.NamedElement;

public class ChangeElementNameCommand extends InputTextCommand {
    private  NamedElement nameType;
    private String oldName;
    private String newName;
    
    public ChangeElementNameCommand(NamedElement field){
        this.nameType = field;
        oldName = field.getName();
    }
    
    public void execute() {
        newName = getInputText();
        redo();
    }

    public String getLabel() {
        return "Change name";
    }

    public void redo() {
        nameType.setName(newName);
    }

    public void undo() {
        nameType.setName(oldName);
    }
}