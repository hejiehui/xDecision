package com.xrosstools.xdecision.ext;

import com.xrosstools.xdecision.Facts;

public class ElementOfExpression extends LeftExpression {
    private Expression index;

    public ElementOfExpression(Expression index) {
        this.index = index;
    }

    @Override
    public Object evaluate(Facts facts) {
        Object[] list = (Object[])leftExp.evaluate(facts);
        return list[(int)index.evaluate(facts)];
    }
}
