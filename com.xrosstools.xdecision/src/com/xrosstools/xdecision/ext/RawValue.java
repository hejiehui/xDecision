package com.xrosstools.xdecision.ext;

import com.xrosstools.xdecision.Facts;

public class RawValue implements Expression {
    private Object value;
    
    public RawValue(Double value) {
        this.value = value;
    }

    public RawValue(CharSequence value) {
        this.value = value;
    }

    public RawValue(Token token) {
        this.value = token.getType() == TokenType.STRING ? token.getValueStr() : Double.valueOf(token.getValueStr());
    }

    public RawValue(Object value) {
        this.value = value;
    }

    @Override
    public Object evaluate(Facts facts) {
        return value;
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }        
}
