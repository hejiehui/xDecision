package com.xrosstools.xdecision.idea.editor.commands.definition;

import com.xrosstools.gef.commands.InputTextCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElement;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElementContainer;

public class CreateElementCommand extends InputTextCommand {
    protected DecisionTreeDiagram diagram;
    private NamedElementContainer<NamedElement> container;
    protected NamedElement newElement;
    
    public CreateElementCommand(DecisionTreeDiagram diagram, NamedElementContainer container){
        this.diagram = diagram;
        this.container = container;
    }
    
    public boolean canExecute() {
        return !contains(diagram, container, getInputText());
    }
     
    public static boolean contains(DecisionTreeDiagram diagram, NamedElementContainer<NamedElement> container, String newName) {
        switch (container.getElementType()) {
        case DECISION:
        case DATA_TYPE:
            return !container.containsName(newName);
        case FACTOR:
        case ENUM:
        case CONSTANT:
            return !(diagram.getFactors().containsName(newName) ||
                    diagram.getUserDefinedConstants().containsName(newName) ||
                    diagram.getUserDefinedEnums().containsName(newName));    
        default:
            return false;
        }
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
