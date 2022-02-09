package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.IdentifierContainer;
import com.xrosstools.xdecision.editor.model.NamedType;
import com.xrosstools.xdecision.editor.model.expression.Identifier;

public class CreateIdentifierCommand extends InputTextCommand{
    private IdentifierContainer container;
    private Identifier newElement;
    private DataType fieldType;
    
    public CreateIdentifierCommand(IdentifierContainer container, NamedType newElement, DataType fieldType){
        this.container = container;
        this.fieldType = fieldType;
        this.newElement = newElement;
    }
    
    public void execute() {
        //TODO check duplicate
        newElement.setType(fieldType);
        newElement.setName(getInputText());
        redo();
    }

    public String getLabel() {
        return "Create new element";
    }

    public void redo() {
        container.add(newElement);
    }

    public void undo() {
        container.remove(newElement);
    }
}
