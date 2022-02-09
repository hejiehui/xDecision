package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeConstant;

public class CreateConstantCommand extends InputTextCommand{
    private DecisionTreeDiagram diagram;
    private DecisionTreeConstant constant;
    private String type;
    
    public CreateConstantCommand(DecisionTreeDiagram diagram, String type){
        this.diagram= diagram;
        this.type = type;
    }
    
    public void execute() {
        constant = new DecisionTreeConstant();
        constant.setTypeName(type);
        constant.setName(getInputText());
        redo();
    }

    public String getLabel() {
        return "Create new constant";
    }

    public void redo() {
        diagram.getUserDefinedConstants().add(constant);
    }

    public void undo() {
        diagram.getUserDefinedConstants().remove(constant);
    }
}
