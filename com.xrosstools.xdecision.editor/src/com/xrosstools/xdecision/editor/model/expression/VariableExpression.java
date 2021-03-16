package com.xrosstools.xdecision.editor.model.expression;

public class VariableExpression extends ExtensibleExpression implements Identifier {
    private String name;

    public VariableExpression(String name) {
        this.name = name;
    }
    
    public String getDisplayText() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        propertyChanged();
    }
    
    public String getMainExpDisplayText() {
        return getDisplayText();
    }
}
