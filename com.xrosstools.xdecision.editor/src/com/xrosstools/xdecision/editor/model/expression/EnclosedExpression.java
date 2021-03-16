package com.xrosstools.xdecision.editor.model.expression;

public class EnclosedExpression extends ExpressionDefinition {
    private ExpressionDefinition enclosedExpression;
    
    public ExpressionDefinition getEnclosedExpression() {
        return enclosedExpression;
    }
    public EnclosedExpression setEnclosedExpression(ExpressionDefinition enclosedExpression) {
        this.enclosedExpression = enclosedExpression;
        return this;
    }
}
