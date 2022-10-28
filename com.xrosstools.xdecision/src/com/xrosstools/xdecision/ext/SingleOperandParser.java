package com.xrosstools.xdecision.ext;

public class SingleOperandParser extends OperandParser {
    public Expression parse(String nodeExpression, String operator, String pathExpression) {
        if(pathExpression != null && pathExpression.length() != 0)
            throw new IllegalArgumentException(String.format("s% does not require extra parameter s%", operator, pathExpression));
        
        return new SingleOperandExpression(parseNodeExpression(nodeExpression), operator);
    }
}
