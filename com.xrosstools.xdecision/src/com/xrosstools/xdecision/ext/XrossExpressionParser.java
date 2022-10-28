package com.xrosstools.xdecision.ext;

import java.util.List;

public class XrossExpressionParser {
    private TokenParser tokenParser = new TokenParser();
    private ExpressionCompiler compiler = new ExpressionCompiler();
    
    public Expression parseNodeExpression(String expressionStr) {
        List<Token> tokens = tokenParser.parseToken(expressionStr);
        
        return compiler.compile(tokens);
    }    
    
    public ParametersExpression parseParameterExpressions(String expressionStr) {
        List<Token> tokens = tokenParser.parseToken(expressionStr);
        
        return (ParametersExpression)compiler.compile(ExpressionType.P, tokens);        
    }
}
