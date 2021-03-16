package com.xrosstools.xdecision.editor.model.expression;

public class IdentityExpression extends BasicExpression {
    private String name;
    
    public IdentityExpression(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        propertyChanged();
    }

    @Override
    public String getDisplayText() {
        return getName();
    }
}
