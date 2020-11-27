package com.xrosstools.xdecision;

public interface PathEvaluator {
    Object evaluate(Facts facts, String factorExpression, Object[] paths); 
}
