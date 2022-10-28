package com.xrosstools.xdecision.ext;

import com.xrosstools.xdecision.Facts;

public class BetweenExpression extends SingleOperandExpression {
    public Expression lowerExp;
    public Expression upperExp;
    
    public BetweenExpression(Expression leftExp, String operator, Expression lowerExp, Expression upperExp) {
        super(leftExp, operator);
        this.lowerExp = lowerExp;
        this.upperExp = upperExp;
    }

    @Override
    public Boolean evaluate(Facts facts) {
        Object value = leftExp.evaluate(facts);
        Object lower = lowerExp.evaluate(facts);
        Object upper = upperExp.evaluate(facts);
        
        if(value == null || lower == null || upper == null)
            return false;
        
        switch (operator) {
        case BETWEEN:
            return compare(value, lower) >= 0 && compare(value, upper) <= 0; 
        case NOT_BETWEEN:
            return compare(value, lower) < 0 || compare(value, upper) > 0; 
        default:
            throw new IllegalArgumentException("Operator: " + operator + " is not supported!");
        }
    }
    
    @Override
    public String getPath() {
        return String.format("%s, %s", String.valueOf(lowerExp), String.valueOf(upperExp));
    }
}
