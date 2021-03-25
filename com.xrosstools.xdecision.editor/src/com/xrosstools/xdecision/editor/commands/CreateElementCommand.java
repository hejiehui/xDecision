package com.xrosstools.xdecision.editor.commands;

import java.util.List;

import com.xrosstools.xdecision.editor.model.FieldDefinition;

public class CreateElementCommand extends InputTextCommand{
    private List elements;
    private FieldDefinition newElement;
    private String fieldType;
    
    public CreateElementCommand(List elements, FieldDefinition newElement, String fieldType){
        this.elements = elements;
        this.fieldType = fieldType;
        this.newElement = newElement;
    }
    
    public void execute() {
        newElement.setTypeName(fieldType);
        newElement.setName(getInputText());
        redo();
    }

    public String getLabel() {
        return "Create new field";
    }

    public void redo() {
        elements.add(newElement);
    }

    public void undo() {
        elements.remove(newElement);
    }
}
