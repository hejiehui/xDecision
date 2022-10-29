package com.xrosstools.xdecision.ext;

import java.util.regex.Pattern;

import com.xrosstools.xdecision.Facts;

public class DoubleOperandExpression extends SingleOperandExpression {
    public Expression rightExp;
    
    public DoubleOperandExpression(Expression leftExp, String operator, Expression rightExp) {
        super(leftExp, operator);
        this.rightExp = rightExp;
    }

    @Override
    public Boolean evaluate(Facts facts) {
        Object v1 = leftExp.evaluate(facts);
        Object v2 = rightExp.evaluate(facts);
        
        switch (operator) {
        case EQUAL:
            return isAllNull(v1, v2) ? true : isNoneNull(v1, v2) ? equals(v1, v2) : false; 
        case NOT_EQUAL:
            return isAllNull(v1, v2) ? false : isNoneNull(v1, v2) ? !equals(v1, v2) : true;
        case GREATER_THAN:
            return isAnyNull(v1, v2) ? false : compare(v1, v2) > 0;
        case GREATER_THAN_EQUAL:
            return isAllNull(v1, v2) ? true : isNoneNull(v1, v2) ? compare(v1, v2) >= 0 : false;
        case LESS_THAN:
            return isNoneNull(v1, v2) ? compare(v1, v2) < 0 : false;
        case LESS_THAN_EQUAL:
            return isAllNull(v1, v2) ? true : isNoneNull(v1, v2) ? compare(v1, v2) <= 0 : false;
        case STARTS_WITH:
            return isNoneNull(v1, v2) && isBothString(v1, v2) ? string(v1).startsWith(string(v2)) : false;
        case NOT_STARTS_WITH:
            return isNoneNull(v1, v2) && isBothString(v1, v2) ? !string(v1).startsWith(string(v2)) : false;
        case ENDS_WITH:
            return isNoneNull(v1, v2) && isBothString(v1, v2) ? string(v1).endsWith(string(v2)) : false;
        case NOT_ENDS_WITH:
            return isNoneNull(v1, v2) && isBothString(v1, v2) ? !string(v1).endsWith(string(v2)) : false;
        case CONTAINS:
            return isNoneNull(v1, v2) && isBothString(v1, v2) ? string(v1).contains(string(v2)) : false;
        case NOT_CONTAINS:
            return isNoneNull(v1, v2) && isBothString(v1, v2) ? !string(v1).contains(string(v2)) : false;
        case MATCHES:
            return isNoneNull(v1, v2) && isBothString(v1, v2) ? Pattern.matches(string(v2), string(v1)) : false;
        case NOT_MATCHES:
            return isNoneNull(v1, v2) && isBothString(v1, v2) ? !Pattern.matches(string(v2), string(v1)) : false;
        default:
            throw new IllegalArgumentException("Operator: " + operator + " is not supported!");
        }
    }
    
    private boolean equals(Object v1, Object v2){
        if(v1 instanceof EnumValue)
            return ((EnumValue)v1).equals(v2);
        
        if(v2 instanceof EnumValue)
            return ((EnumValue)v2).equals(v1);
            
        return v1.equals(v2);
    }

    @Override
    public String getPath() {
        return String.valueOf(rightExp);
    }

    private static boolean isAnyNull(Object v1, Object v2) {
        return v1 == null || v2 == null;
    }

    private static boolean isAllNull(Object v1, Object v2) {
        return v1 == null && v2 == null;
    }

    private static boolean isNoneNull(Object v1, Object v2) {
        return v1 != null && v2 != null;
    }

    private static boolean isBothString(Object v1, Object v2) {
        return v1 instanceof String && v2 instanceof String ;
    }

    private static String string(Object value) {
        if(value instanceof String)
            return (String)value;
        
        throw new IllegalArgumentException(String.valueOf(value) + " is not a string type");
    }        
}
