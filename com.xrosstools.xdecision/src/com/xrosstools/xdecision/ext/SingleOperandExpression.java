package com.xrosstools.xdecision.ext;

import com.xrosstools.xdecision.Facts;

public class SingleOperandExpression extends OperandExpression {
    public Expression leftExp;
    
    public SingleOperandExpression(Expression leftExp, String operator) {
        super(operator);
        this.leftExp = leftExp;
    }

    @Override
    public Boolean evaluate(Facts facts) {
        switch (operator) {
        case IS_NULL:
            return leftExp.evaluate(facts) == null; 
        case IS_NOT_NULL:
            return leftExp.evaluate(facts) != null; 
        case IS_TRUE:
            return leftExp.evaluate(facts) == Boolean.TRUE; 
        case IS_FALSE:
            return leftExp.evaluate(facts) == Boolean.FALSE; 
        default:
            throw new IllegalArgumentException("Operator: " + operator + " is not supported!");
        }
    }        

    @Override
    public String getPath() {
        return "";
    }
}
