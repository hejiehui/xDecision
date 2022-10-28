package com.xrosstools.xdecision.ext;

import java.util.List;

public class InParser extends OperandParser {
    public Expression parse(String nodeExpression, String operator, String pathExpression) {
        List<Expression> params = parseParameterExpressions(pathExpression);
        if(params.size() == 0)
            throw new IllegalArgumentException("IN/NOT IN requires at least one parameter");

        return new InExpression(parseNodeExpression(nodeExpression), operator, params.toArray(new Expression[params.size()]));
    }
}
