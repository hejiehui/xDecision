package com.xrosstools.xdecision.editor.model.expression;

public enum TokenType {
    ID(){
        public boolean matchStart(char next) {
            return (next >= 'a' && next <= 'z') || (next >= 'A' && next <= 'Z') || next == '_';
        }

        public boolean matchBody(StringBuilder buf, char next) {
            return matchStart(next) || (next >= '0' && next <= '9');
        }
    }, 

    STRING(){
        public boolean matchStart(char next) {
            return next == '\'';
        }

        public boolean matchBody(StringBuilder buf, char next) {
            return buf.length() == 1 || !matchStart(buf.charAt(buf.length() -1));
        }

        public Token parse(StringBuilder buf) {
            buf.deleteCharAt(0);
            buf.deleteCharAt(buf.length()-1);
            return new Token(this, buf.toString());
        }

    }, 

    NUMBER(){
        public boolean matchStart(char next) {
            return next >= '0' && next <= '9';
        }

        public boolean matchBody(StringBuilder buf, char next) {
            return matchStart(next) || next == '.';
        }
    }, 

    DOT('.'),
    LBRKT('('),
    RBRKT(')'),
    LSBRKT('['),
    RSBRKT(']'),
    COMMA(','),
    PLUS('+'),
    MINUS('-'),
    TIMES('*'),
    DIVIDE('/');
    
    
    private char startChar;
    
    private TokenType() {}

    private TokenType(char startChar) {
        this.startChar = startChar;
    }

    public boolean matchStart(char next) {
        return startChar == next;
    }

    public boolean matchBody(StringBuilder buf, char next) {
        return false;
    }

    public Token parse(StringBuilder buf) {
        return new Token(this, buf.toString());
    }
}
