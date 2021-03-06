package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;

public class CreateFactorCommand extends InputTextCommand {
    private DecisionTreeDiagram diagram;
    private String type;
    private DecisionTreeFactor factor;

    public CreateFactorCommand(DecisionTreeDiagram diagram, String factorType) {
        this.diagram = diagram;
        this.type = factorType;
    }
    
    public void execute() {
        factor = new DecisionTreeFactor();
        factor.setFactorName(getInputText());
        factor.setTypeName(type);
        
        redo();
    }

    public String getLabel() {
        return "Create new factor";
    }

    public void redo() {
        diagram.getFactors().add(factor);
    }

    public void undo() {
        diagram.getFactors().remove(factor);
    }
}
