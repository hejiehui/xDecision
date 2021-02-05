package com.xrosstools.xdecision.ext;

import com.xrosstools.xdecision.Facts;

public class NegtiveExpression implements Expression {
    private Expression exp;

    public NegtiveExpression(Expression exp) {
        this.exp = exp;
    }
    
    @Override
    public Number evaluate(Facts facts) {
        Number num = ((Number)exp.evaluate(facts));

        if(num instanceof Integer)
            return num.intValue() * -1;
        
        if(num instanceof Long)
            return num.longValue() * -1;
        
        if(num instanceof Double)
            return num.doubleValue() * -1;

        if(num instanceof Float)
            return num.floatValue() * -1;

        if(num instanceof Short)
            return num.shortValue() * -1;

        //Only Byte left
        return num.byteValue() * -1;
    }
}
