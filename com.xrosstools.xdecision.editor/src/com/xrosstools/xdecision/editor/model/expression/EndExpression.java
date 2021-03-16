package com.xrosstools.xdecision.editor.model.expression;

public class EndExpression extends ExtensibleExpression {
    public static ExpressionDefinition end() {
        return new EndExpression();
    }
    
    public void setChild(ExpressionDefinition child) {
    }

    @Override
    public String getMainExpDisplayText() {
        throw new IllegalStateException();
    }
}
