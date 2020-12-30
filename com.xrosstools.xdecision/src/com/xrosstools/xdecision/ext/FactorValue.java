package com.xrosstools.xdecision.ext;

import com.xrosstools.xdecision.Facts;

public class FactorValue implements Expression {
    private String factorName;
    
    public FactorValue(String factorName) {
        this.factorName = factorName;
    }

    @Override
    public Object evaluate(Facts facts) {
        return facts.get(factorName);
    }
    
    @Override
    public String toString() {
        return factorName;
    }
}
