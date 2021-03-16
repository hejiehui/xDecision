package com.xrosstools.xdecision.editor.model.expression;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;

public class FactorNameExpression extends IdentityExpression {
    private DecisionTreeDiagram diagram;
    private int factorId;

    public FactorNameExpression(DecisionTreeDiagram diagram, int factorId) {
        super(diagram.getFactorById(factorId).getFactorName());
        this.diagram = diagram;
        this.factorId = factorId;
    }

    public int getFactorId() {
        return factorId;
    }
    public void setFactorId(int factorId) {
        this.factorId = factorId;
        setName(getFactor().getFactorName());
    }
    
    public DecisionTreeDiagram getDiagram() {
        return diagram;
    }

    public DecisionTreeFactor getFactor() {
        return getDiagram().getFactorById(factorId);
    }
}
