package com.xrosstools.xdecision.editor.model.expression;

public class VariableExpression extends ExtensibleExpression implements Identifier {
    private String name;
    private boolean valid = true;

    public VariableExpression(String name) {
        this.name = name;
    }
    
    public String getIdentifier() {
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
        return getIdentifier();
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
        propertyChanged();
    }
}
