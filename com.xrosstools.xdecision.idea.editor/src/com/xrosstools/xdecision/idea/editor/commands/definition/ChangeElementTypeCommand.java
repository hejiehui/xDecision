package com.xrosstools.xdecision.idea.editor.commands.definition;

import com.xrosstools.gef.commands.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.definition.DataType;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedType;

public class ChangeElementTypeCommand extends Command {
    private DecisionTreeDiagram diagram;
    private NamedType typeNameSupported;
    private String newTypeName;
    private DataType oldType;
    private DataType newType;
    
    public ChangeElementTypeCommand(DecisionTreeDiagram diagram, NamedType typeNameSupported, String newTypeName){
        this.diagram = diagram;
        this.typeNameSupported = typeNameSupported;
        this.newTypeName = newTypeName;
        this.oldType = typeNameSupported.getType();
    }
    
    public void execute() {
        newType = diagram.findDataType(newTypeName);
        redo();
    }

    public String getLabel() {
        return "Change Data Type";
    }

    public void redo() {
        typeNameSupported.setType(newType);
    }

    public void undo() {
        typeNameSupported.setType(oldType);
    }
}
