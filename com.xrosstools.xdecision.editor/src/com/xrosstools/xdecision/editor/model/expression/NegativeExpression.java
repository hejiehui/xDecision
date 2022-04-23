package com.xrosstools.xdecision.editor.model.expression;

public class NegativeExpression extends EnclosedExpression {
    @Override
    public String toString() {
        return "-" + getInnerExpression().toString();
    }
}
