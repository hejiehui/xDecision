package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.UserDefinedType;

public class CreateUserDefineidTypeCommand extends InputTextCommand{
    private DecisionTreeDiagram diagram;
    private UserDefinedType newType;
    
    public CreateUserDefineidTypeCommand(DecisionTreeDiagram diagram){
        this.diagram = diagram;
    }
    
    public void execute() {
        newType = new UserDefinedType();
        newType.setName(getInputText());
        diagram.getUserDefinedTypes().add(newType);
    }

    public String getLabel() {
        return "Create new decision";
    }

    public void redo() {
        diagram.getUserDefinedTypes().add(newType);
    }

    public void undo() {
        diagram.getUserDefinedTypes().remove(newType);
    }
}
