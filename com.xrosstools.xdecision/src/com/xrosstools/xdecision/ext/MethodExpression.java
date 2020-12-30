package com.xrosstools.xdecision.ext;

import com.xrosstools.xdecision.Facts;

public class MethodExpression extends LeftExpression {
    private String methodName;
    private ParametersExpression parameters;
    
    public MethodExpression(ParametersExpression parameters) {
        super();
        this.methodName = methodName;
        this.parameters = parameters;
    }

    @Override
    public Object evaluate(Facts facts) {
        // TODO Auto-generated method stub
        return null;
    }

}
