package com.xrosstools.xdecision.ext;

import java.util.List;

public class XrossExpressionParser {
    private TokenParser tokenParser = new TokenParser();
    private ExpressionCompiler compiler = new ExpressionCompiler();
    
    public Object parseExpression(String expressionStr) {
        List<Token> tokens = tokenParser.parseToken(expressionStr);
        
        return compiler.compile(tokens);
    }    
}
