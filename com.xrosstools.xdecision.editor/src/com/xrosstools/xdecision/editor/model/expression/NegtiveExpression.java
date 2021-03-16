package com.xrosstools.xdecision.editor.model.expression;

public class NegtiveExpression extends EnclosedExpression {
    @Override
    public String toString() {
        return "-" + getEnclosedExpression().toString();
    }
}
