package com.xrosstools.xdecision.ext;

import java.util.List;

public class DoubleOperandParser extends OperandParser {
    public Expression parse(String nodeExpression, String operator, String pathExpression) {
        List<Expression> params = parseParameterExpressions(pathExpression);
        if(params.size() != 1)
            throw new IllegalArgumentException("Only one expression is allowed: " + pathExpression);

        return new DoubleOperandExpression(parseNodeExpression(nodeExpression), operator, params.get(0));
    }
}
