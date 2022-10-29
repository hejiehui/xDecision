package com.xrosstools.xdecision.ext;

import com.xrosstools.xdecision.Facts;

public class RawValue implements Expression {
    public static final RawValue NULL_OBJ = new RawValue(null);
    
    private Object value;
    
    public static RawValue numberOf(CharSequence value) {
        return new RawValue(parseNumber(value));
    }

    public static Number parseNumber(CharSequence value) {
        boolean isDouble = false;
        for(int i = 0; i < value.length(); i++) {
            if(value.charAt(i) == '.')
                isDouble = true;
        }
        
        if(isDouble)
            return new Double(value.toString());
        else
            return new Integer(value.toString());
    }

    public static RawValue stringOf(CharSequence value) {
        return new RawValue(value.toString());
    }

    public static RawValue tokenOf(Token token) {
        return token.getType() == TokenType.STRING ? stringOf(token.getValueStr()) : numberOf(token.getValueStr());
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
