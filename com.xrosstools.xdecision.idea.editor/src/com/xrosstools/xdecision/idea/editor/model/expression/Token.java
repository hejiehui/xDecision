package com.xrosstools.xdecision.idea.editor.model.expression;

public class Token {
    private TokenType type;
    private String valueStr;

    public Token(TokenType type, String valueStr) {
        this.type = type;
        this.valueStr = valueStr;
    }

    public TokenType getType() {
        return type;
    }
    public String getValueStr() {
        return valueStr;
    }
}
