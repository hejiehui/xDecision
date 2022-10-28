package com.xrosstools.xdecision.ext;

public abstract class OperandExpression implements Expression, XrossEvaluatorConstants {
    protected String operator;
    
    public OperandExpression(String operator) {
        this.operator = operator;
    }
    
    public int compare(Object v1, Object v2) {
        if(v1 instanceof Number && v2 instanceof Number)
            return Double.compare(((Number)v1).doubleValue(), ((Number)v2).doubleValue());
        
        if(v1 instanceof String && v2 instanceof String)
            return ((String)v1).compareTo((String)v2);
        
        if(v1 instanceof Comparable && v2 instanceof Comparable)
            return ((Comparable)v1).compareTo((Comparable)v2);
        
        if(v1 == v2 || v1.equals(v2))
            return 0;
        
        throw new IllegalArgumentException(String.valueOf(v1) + " can not be compared to " + v2);
    }
    
    @Override
    public String toString() {
        return String.format("%s %s", operator, getPath());
    }
    
    abstract public String getPath();
}
