package com.xrosstools.xdecision.ext;

import com.xrosstools.xdecision.Facts;

public class CalculateExpression extends LeftExpression {
    private boolean revseLeft;
    private Expression rightExp;
    private TokenType operator;


    public CalculateExpression(Expression rightExp, TokenType operator) {
        if(operator == TokenType.MINUS && rightExp instanceof CalculateExpression) {
            CalculateExpression rightCal = (CalculateExpression)rightExp;
//            if(rightCal.operator == TokenType.MINUS || rightCal.operator == TokenType.PLUS) {
                rightCal.revseLeft = true;
                operator = TokenType.PLUS;
//            }
        }        
        
        this.rightExp = rightExp;
        this.operator = operator;
    }
    
    @Override
    public Double evaluate(Facts facts) {
        double leftValue = revseLeft ? -1 * eval(leftExp, facts) : eval(leftExp, facts);
            
        switch (operator) {
        case PLUS:
            return leftValue + eval(rightExp, facts);
        case MINUS:
            return leftValue - eval(rightExp, facts);
        case TIMES:
            return leftValue * eval(rightExp, facts);
        case DIVIDE:
            return leftValue / eval(rightExp, facts);
        default:
            throw new IllegalArgumentException(operator.toString() + " is not supported");
        }
    }
    
    private double eval(Expression exp, Facts facts) {
        return ((Number)exp.evaluate(facts)).doubleValue();
    }
}
