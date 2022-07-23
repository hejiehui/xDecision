package com.xrosstools.xdecision.editor.commands.definition;

import com.xrosstools.xdecision.editor.commands.InputTextCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.definition.NamedElement;
import com.xrosstools.xdecision.editor.model.definition.NamedElementContainer;

public class ChangeElementNameCommand extends InputTextCommand {
    private DecisionTreeDiagram diagram;
    private NamedElementContainer<NamedElement> container;
    private NamedElement nameType;
    private String oldName;
    private String newName;
    
    public ChangeElementNameCommand(DecisionTreeDiagram diagram, NamedElementContainer<NamedElement> container, NamedElement field){
        this.diagram = diagram;
        this.container = container;
        this.nameType = field;
        oldName = field.getName();
    }
    
    public boolean canExecute() {
        return !CreateElementCommand.contains(diagram, container, getInputText());
    }
    
    public void execute() {
        redo();
    }

    public String getLabel() {
        return "Change name";
    }

    public void redo() {
        nameType.setName(getInputText());
    }

    public void undo() {
        nameType.setName(oldName);
    }
}