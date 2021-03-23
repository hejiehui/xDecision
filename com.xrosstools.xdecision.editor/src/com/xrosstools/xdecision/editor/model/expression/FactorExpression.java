package com.xrosstools.xdecision.editor.model.expression;

import com.xrosstools.xdecision.editor.model.DecisionTreeManager;

public class FactorExpression extends ExtensibleExpression implements Identifier {
    private DecisionTreeManager manager;
    private int factorId;

    public FactorExpression(DecisionTreeManager manager, String factorName) {
        this(manager, 1);
    }

    public FactorExpression(DecisionTreeManager manager, int factorId) {
        this.manager = manager;
        this.factorId = factorId;
    }
    
    public void setManager(DecisionTreeManager manager) {
        this.manager = manager;
    }
    public int getFactorId() {
        return factorId;
    }

    public String getIdentifier() {
        return manager.getFactor(factorId).getFactorName();
    }

    public void setFactorId(int factorId) {
        this.factorId = factorId;
        propertyChanged(); 
    }
    
    public String getMainExpDisplayText() {
        return getIdentifier();
    }
}
