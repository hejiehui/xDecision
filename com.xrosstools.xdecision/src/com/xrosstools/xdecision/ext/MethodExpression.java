package com.xrosstools.xdecision.ext;

import java.lang.reflect.Method;

import com.xrosstools.xdecision.Facts;

public class MethodExpression extends LeftExpression {
    private Expression methodNameExp;
    private ParametersExpression parameters;
    
    public MethodExpression(ParametersExpression parameters) {
        super();
        this.parameters = parameters;
    }
    
    public Expression setLeftExp(Expression leftExp) {
        if(methodNameExp == null && !(leftExp instanceof EndExpression)) {
            methodNameExp = leftExp;
            return this;
        }
        
        return super.setLeftExp(leftExp);
    }

    @Override
    public Object evaluate(Facts facts) {
        //TODO support default method
        Object target = leftExp.evaluate(facts);
        String methodName = (String)methodNameExp.toString();
        
        Object[] values = (Object[])parameters.evaluate(facts);
        Class<?>[] pTypes = parameters.getParameterTypes(values);
        
        try {
            Method m = target.getClass().getMethod(methodName, pTypes);
            return m.invoke(target, values);
            
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
