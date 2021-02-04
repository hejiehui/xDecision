package com.xrosstools.xdecision.ext;

public abstract class LeftExpression implements Expression {
    protected Expression leftExp;

    public Expression setLeftExp(Expression leftExp) {
        if(leftExp instanceof EndExpression)
            return this;

        if(this.leftExp == null) {
            this.leftExp = leftExp;
            return this;
        }
        
        if(this.leftExp instanceof LeftExpression)
            return ((LeftExpression)this.leftExp).setLeftExp(leftExp);
        else
            throw new IllegalArgumentException(leftExp.toString() + " is not allowed");
    }
}
