package com.xrosstools.xdecision.idea.editor.model.expression;

public abstract class BasicExpression extends ExpressionDefinition {
    public abstract String getDisplayText();

    @Override
    public String toString() {
        return getDisplayText();
    }
}
