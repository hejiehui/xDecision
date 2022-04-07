package com.xrosstools.xdecision.editor.model.expression;

public class EnclosedExpression extends ExpressionDefinition {
    private ExpressionDefinition innerExpression;
    
    public ExpressionDefinition getInnerExpression() {
        return innerExpression;
    }
    public EnclosedExpression setInnerExpression(ExpressionDefinition innerExpression) {
        this.innerExpression = innerExpression;
        if(innerExpression != null)
            innerExpression.getListeners().addPropertyChangeListener(this);
        propertyChanged();
        return this;
    }
}
