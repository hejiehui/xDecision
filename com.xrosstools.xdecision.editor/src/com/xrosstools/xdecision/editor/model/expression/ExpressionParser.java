package com.xrosstools.xdecision.editor.model.expression;

import com.xrosstools.xdecision.editor.model.DecisionTreeManager;

public class ExpressionParser {
    private DecisionTreeManager manager;
    private TokenParser tokenParser = new TokenParser();
    private ExpressionCompiler compiler = new ExpressionCompiler();

    public ExpressionParser(DecisionTreeManager manager) {
        this.manager = manager;
    }
    public ExpressionDefinition parse(String expressionRawText) {
        return compiler.compile(tokenParser.parseToken(expressionRawText));
    }
}
