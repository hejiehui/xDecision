package com.xrosstools.xdecision.editor.model.expression;

public class BracktExpression extends EnclosedExpression {
    @Override
    public String toString() {
        return "(" + (getInnerExpression() == null ? "" : getInnerExpression().toString()) + ")";
    }
}
