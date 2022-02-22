package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;

public class CreateUserDefineidTypeCommand extends InputTextCommand{
    private DecisionTreeDiagram diagram;
    private DataType newType;
    
    public CreateUserDefineidTypeCommand(DecisionTreeDiagram diagram){
        this.diagram = diagram;
    }
    
    public void execute() {
        newType = new DataType(getInputText());
        redo();
    }

    public String getLabel() {
        return "Create new decision";
    }

    public void redo() {
        diagram.getUserDefinedTypeList().add(newType);
    }

    public void undo() {
        diagram.getUserDefinedTypeList().remove(newType);
    }
}
