package com.xrosstools.xdecision.editor.commands;

import java.util.List;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.FieldDefinition;

public class DeleteElementCommand extends Command{
    private List elements;
    private FieldDefinition element;
    
    public DeleteElementCommand(
            List elements,
            FieldDefinition element){
        this.elements = elements;
        this.element = element;
    }
    
    public void execute() {
        redo();
    }

    public String getLabel() {
        return "Delete field";
    }

    public void redo() {
        elements.remove(element);
    }

    public void undo() {
        elements.add(element);
    }
}
