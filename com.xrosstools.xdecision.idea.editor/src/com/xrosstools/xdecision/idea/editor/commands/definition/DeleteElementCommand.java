package com.xrosstools.xdecision.idea.editor.commands.definition;

import com.xrosstools.gef.commands.Command;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElement;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElementContainer;

public class DeleteElementCommand extends Command {
    private NamedElementContainer<NamedElement> container;
    private NamedElement element;
    
    public DeleteElementCommand(
            NamedElementContainer<NamedElement> container,
            NamedElement element){
        this.container = container;
        this.element = element;
    }
    
    public void execute() {
        redo();
    }

    public String getLabel() {
        return "Delete element";
    }

    public void redo() {
        container.remove(element);
    }

    public void undo() {
        container.add(element);
    }
}
