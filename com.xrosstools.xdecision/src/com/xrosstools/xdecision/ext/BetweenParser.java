package com.xrosstools.xdecision.ext;

import java.util.List;

public class BetweenParser extends OperandParser {
    public Expression parse(String nodeExpression, String operator, String pathExpression) {
        List<Expression> params = parseParameterExpressions(pathExpression);
        if(params.size() != 2)
            throw new IllegalArgumentException(String.format("BETWEEN/NOT BETWEEN requires exactly 2 parameters. Please check the format of : ", pathExpression));
        
        return new BetweenExpression(parseNodeExpression(nodeExpression), operator, params.get(0), params.get(1));
    }
}
