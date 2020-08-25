package com.xrosstools.xdecision;

public interface PathEvaluator {
    Object evaluate(Facts facts, String factorName, Object[] paths); 
}
