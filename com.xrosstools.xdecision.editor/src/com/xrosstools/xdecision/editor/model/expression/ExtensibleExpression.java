package com.xrosstools.xdecision.editor.model.expression;

public abstract class ExtensibleExpression extends ExpressionDefinition {
    private ExpressionDefinition childExpression;

    public ExpressionDefinition getChildExpression() {
        return childExpression;
    }

    public void setChildExpression(ExpressionDefinition expression) {
        this.childExpression = expression;
        this.childExpression.getListeners().addPropertyChangeListener(this);
        propertyChanged();
    }

    public boolean hasChild() {
        return childExpression != null;
    }
    
    public boolean isElementChild() {
        return hasChild() && childExpression instanceof ElementExpression;
    }


    @Override
    public final String toString() {
        if(!hasChild())
            return getBaseString();
        
        return getBaseString() + (isElementChild() ? "" : ".") + childExpression;
    }

    public abstract String getBaseString();
}