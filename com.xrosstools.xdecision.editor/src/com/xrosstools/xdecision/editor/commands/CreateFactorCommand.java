package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.FactorType;

public class CreateFactorCommand extends InputTextCommand {
    private DecisionTreeDiagram diagram;
    private FactorType factorType;
    private DecisionTreeFactor factor;
    private String customizedType;

    public CreateFactorCommand(DecisionTreeDiagram diagram, FactorType factorType) {
        this.diagram = diagram;
        this.factorType = factorType;
    }

    public CreateFactorCommand(DecisionTreeDiagram diagram, FactorType factorType, String customizedType){
        this(diagram, factorType);
        this.customizedType = customizedType;
    }
    
    public void execute() {
        factor = new DecisionTreeFactor();
        factor.setFactorName(getInputText());
        factor.setType(factorType);
        factor.setCustomizedType(customizedType);
        
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
