package com.xrosstools.xdecision.editor.model.expression;

public class ExtensibleExpression extends ExpressionDefinition {
    private ExpressionDefinition baseExpression;
    private ExpressionDefinition childExpression;

    public ExtensibleExpression (ExpressionDefinition baseExpression) {
        setBaseExpression(baseExpression);
    }

    public ExpressionDefinition getBaseExpression() {
        return baseExpression;
    }

    public void setBaseExpression(ExpressionDefinition baseExpression) {
        this.baseExpression = baseExpression;
        baseExpression.getListeners().addPropertyChangeListener(this);
        propertyChanged();
    }

    public ExpressionDefinition getChild() {
        return childExpression;
    }

    public void setChild(ExpressionDefinition childExpression) {
        this.childExpression = childExpression;
        childExpression.getListeners().addPropertyChangeListener(this);
        propertyChanged();
    }

    public boolean hasChild() {
        return childExpression != null;
    }

    @Override
    public String toString() {
        return baseExpression.toString() + (hasChild() ? "." + childExpression.toString() : "");
    }
}