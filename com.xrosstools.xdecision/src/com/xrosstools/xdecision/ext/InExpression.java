package com.xrosstools.xdecision.ext;

import java.util.Collection;

import com.xrosstools.xdecision.Facts;

public class InExpression extends SingleOperandExpression {
    public Expression[] valueRange;
    
    public InExpression(Expression leftExp, String operator, Expression[] valueRange) {
        super(leftExp, operator);
        this.valueRange = valueRange;
    }

    @Override
    public Boolean evaluate(Facts facts) {
        Object fact = leftExp.evaluate(facts);
        
        if(fact == null)
            return false;
        
        switch (operator) {
        case IN:
            return inRange(facts, fact); 
        case NOT_IN:
            return !inRange(facts, fact); 
        default:
            throw new IllegalArgumentException("Operator: " + operator + " is not supported!");
        }
    }
    
    private boolean inRange(Facts facts, Object fact) {
        for(Expression exp: valueRange) {
            Object candidateValue = exp.evaluate(facts);
            if(candidateValue == null)
                continue;
            if(candidateValue instanceof Collection<?>) {
                Collection<?> col = (Collection<?>)candidateValue;
                if(col.contains(fact))
                    return true;
            }else {
                if(compare(fact, candidateValue) == 0)
                    return true;
            }
        }
        return false;
    }

    @Override
    public String getPath() {
        StringBuilder sb = new StringBuilder();
        for(Expression exp: valueRange)
            sb.append(exp.toString()).append(",");
        
        return sb.substring(0,  sb.length()-2);
    }
}