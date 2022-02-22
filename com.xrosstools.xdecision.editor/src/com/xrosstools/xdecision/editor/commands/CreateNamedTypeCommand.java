package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.NamedElementContainer;
import com.xrosstools.xdecision.editor.model.NamedType;

public class CreateNamedTypeCommand extends CreateElementCommand{
    private String fieldType;
    
    public CreateNamedTypeCommand(DecisionTreeDiagram diagram, NamedElementContainer container, String fieldType){
        super(diagram, container);
        this.fieldType = fieldType;
    }
    
    public void execute() {
        super.execute();
        ((NamedType)newElement).setType(diagram.findDataType(fieldType));
    }
}