package com.xrosstools.xdecision.editor.commands.definition;

import com.xrosstools.xdecision.editor.commands.InputTextCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.definition.NamedElement;
import com.xrosstools.xdecision.editor.model.definition.NamedElementContainer;

public class CreateElementCommand extends InputTextCommand{
    protected DecisionTreeDiagram diagram;
    private NamedElementContainer container;
    protected NamedElement newElement;
    
    public CreateElementCommand(DecisionTreeDiagram diagram, NamedElementContainer container){
        this.diagram = diagram;
        this.container = container;
    }
    
    public boolean canExecute() {
        return !container.containsName(getInputText());
    }
    
    public void execute() {
        newElement = container.getElementType().newInstance(diagram, getInputText());
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
