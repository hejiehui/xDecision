package com.xrosstools.xdecision.editor.model.expression;

public abstract class ExtensibleExpression extends ExpressionDefinition {
    private ExpressionDefinition childExpression;

    public ExpressionDefinition getChildExpression() {
        return childExpression;
    }

    public void setChildExpression(ExpressionDefinition expression) {
        this.childExpression = replaceExpression(this.childExpression, expression);
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