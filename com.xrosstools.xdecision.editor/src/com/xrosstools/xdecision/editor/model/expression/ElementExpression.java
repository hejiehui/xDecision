package com.xrosstools.xdecision.editor.model.expression;

public class ElementExpression extends ExpressionDefinition {
    private ExpressionDefinition indexExpression;

    public ElementExpression (ExpressionDefinition indexExpression) {
        setIndexExpression(indexExpression);
    }

    public ExpressionDefinition getIndexExpression() {
        return indexExpression;
    }

    public void setIndexExpression(ExpressionDefinition indexExpression) {
        this.indexExpression = indexExpression;
        indexExpression.getListeners().addPropertyChangeListener(this);
        propertyChanged();
    }

    @Override
    public String toString() {
        return "[" + indexExpression.toString() + "]";
    }
}
