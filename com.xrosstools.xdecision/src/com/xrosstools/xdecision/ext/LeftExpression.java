package com.xrosstools.xdecision.ext;

public abstract class LeftExpression implements Expression {
    protected Expression leftExp;

    public Expression setLeftExp(Expression leftExp) {
        this.leftExp = leftExp;
        
        return this;
    }
}
