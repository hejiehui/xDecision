package com.xrosstools.xdecision.editor.model.expression;

public class ElementExpression extends ExtensibleExpression {
    private ExpressionDefinition indexExpression;

    public ElementExpression (ExpressionDefinition indexExpression) {
        this.indexExpression = indexExpression;
    }

    public ExpressionDefinition getIndexExpression() {
        return indexExpression;
    }

    public void setIndexExpression(ExpressionDefinition indexExpression) {
        this.indexExpression = indexExpression;
    }

    @Override
    public String getMainExpDisplayText() {
        return "[" + indexExpression.toString() + "]";
    }
}
