package com.xrosstools.xdecision.ext;

import java.util.ArrayList;
import java.util.List;

public abstract class OperandParser implements XrossEvaluatorConstants {
    public abstract Expression parse(String nodeExpression, String operator, String pathExpression);

    private TokenParser tokenParser = new TokenParser();
    private ExpressionCompiler compiler = new ExpressionCompiler();
    
    public Expression parseNodeExpression(String expressionStr) {
        List<Token> tokens = tokenParser.parseToken(expressionStr);
        return compiler.compile(tokens);
    }
    
    public List<Expression> parseParameterExpressions(String expressionStr) {
        if(expressionStr == null) {
            ArrayList<Expression> params = new ArrayList<>();
            params.add(RawValue.NULL_OBJ);
            return params;
        }

        List<Token> tokens = tokenParser.parseToken(expressionStr);        
        ParametersExpression params = (ParametersExpression)compiler.compile(ExpressionType.P, tokens);
        return params.getParams();
    }
}
