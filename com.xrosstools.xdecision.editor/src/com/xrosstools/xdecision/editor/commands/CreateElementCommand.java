package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.NamedElement;
import com.xrosstools.xdecision.editor.model.NamedElementContainer;

public class CreateElementCommand extends InputTextCommand{
    private NamedElementContainer container;
    protected NamedElement newElement;
    
    public CreateElementCommand(NamedElementContainer container){
        this.container = container;
        newElement = container.getElementType().newInstance();
    }
    
    public boolean canExecute() {
        return !container.containsName(getInputText());
    }
    
    public void execute() {
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
