package com.xrosstools.xdecision.idea.editor.model.expression;

public class EnclosedExpression extends ExpressionDefinition {
    private ExpressionDefinition innerExpression;
    
    public ExpressionDefinition getInnerExpression() {
        return innerExpression;
    }
    public EnclosedExpression setInnerExpression(ExpressionDefinition innerExpression) {
        unlisten(this.innerExpression);
        this.innerExpression = innerExpression;
        listen(innerExpression);
        propertyChanged();
        return this;
    }
}
