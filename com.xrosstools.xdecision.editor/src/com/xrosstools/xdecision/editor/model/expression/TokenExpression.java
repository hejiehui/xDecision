package com.xrosstools.xdecision.editor.model.expression;

public class TokenExpression extends BasicExpression {
    public static final TokenExpression TOKEN_DOT = new TokenExpression(".");
    public static final TokenExpression TOKEN_COMMA = new TokenExpression(",");
    public static final TokenExpression TOKEN_SPACE = new TokenExpression(" ");
    public static final TokenExpression TOKEN_LBRKT = new TokenExpression("(");
    public static final TokenExpression TOKEN_RBRKT = new TokenExpression(")");
    public static final TokenExpression TOKEN_LSBRKT = new TokenExpression("[");
    public static final TokenExpression TOKEN_RSBRKT = new TokenExpression("]");
    public static final TokenExpression TOKEN_EMPTY = new TokenExpression("");
    
    private String token;
    public TokenExpression(String token) {
        this.token = token;
    }
    
    public String getToken() {
        return token;
    }

    @Override
    public String getDisplayText() {
        return getToken();
    }
}
