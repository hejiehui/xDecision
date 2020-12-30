package com.xrosstools.xdecision.ext;

import com.xrosstools.xdecision.Facts;

public class CalculateExpression extends LeftExpression {
    private Expression rightExp;
    private TokenType operator;


    public CalculateExpression(Expression rightExp, TokenType operator) {
        this.rightExp = rightExp;
        this.operator = operator;
    }
    
    @Override
    public Double evaluate(Facts facts) {
        switch (operator) {
        case PLUS:
            return eval(leftExp, facts) + eval(rightExp, facts);
        case MINUS:
            return eval(leftExp, facts) - eval(rightExp, facts);
        case TIMES:
            return eval(leftExp, facts) * eval(rightExp, facts);
        case DIVIDE:
            return eval(leftExp, facts) / eval(rightExp, facts);
        default:
            throw new IllegalArgumentException(operator.toString() + " is not supported");
        }
    }
    
    private double eval(Expression exp, Facts facts) {
        return ((Number)exp.evaluate(facts)).doubleValue();
    }

}
