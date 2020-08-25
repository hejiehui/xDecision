package com.xrosstools.xdecision;

public class DefaultEvaluator implements PathEvaluator {

    @Override
    public Object evaluate(Facts facts, String factorName, Object[] paths) {
        return facts.get(factorName);
    }
}
