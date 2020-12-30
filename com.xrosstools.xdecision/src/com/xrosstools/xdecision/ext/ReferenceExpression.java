package com.xrosstools.xdecision.ext;

import com.xrosstools.xdecision.Facts;

public class ReferenceExpression implements Expression {
    private String fieldName;

    public ReferenceExpression(String fieldName) {
        super();
        this.fieldName = fieldName;
    }

    @Override
    public Object evaluate(Facts facts) {
        // TODO Auto-generated method stub
        return null;
    }

}
