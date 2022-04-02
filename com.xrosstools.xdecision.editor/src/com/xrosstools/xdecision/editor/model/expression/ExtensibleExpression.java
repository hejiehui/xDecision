package com.xrosstools.xdecision.editor.model.expression;

public abstract class ExtensibleExpression extends ExpressionDefinition {
    private ExpressionDefinition child;

    public ExpressionDefinition getChild() {
        return child;
    }

    public void setChild(ExpressionDefinition child) {
        this.child = child;
        child.getListeners().addPropertyChangeListener(this);
        propertyChanged();
    }

    public boolean hasChild() {
        return child != null;
    }

    @Override
    public String toString() {
        return getMainExpDisplayText() + (hasChild() ? "." + child.toString() : "");
    }

    public abstract String getMainExpDisplayText();
}
