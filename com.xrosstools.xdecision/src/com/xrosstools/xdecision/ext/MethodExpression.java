package com.xrosstools.xdecision.ext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.xrosstools.xdecision.Facts;

public class MethodExpression extends LeftExpression {
    private String methodName;
    private ParametersExpression parameters;
    
    public MethodExpression(ParametersExpression parameters) {
        super();
        this.parameters = parameters;
    }
    
    public Expression setLeftExp(Expression leftExp) {
        if(methodName == null && !(leftExp instanceof EndExpression)) {
            methodName = leftExp.toString();
            return this;
        }
        
        return super.setLeftExp(leftExp);
    }

    @Override
    public Object evaluate(Facts facts) {
        //TODO support default method
        Object target = leftExp.evaluate(facts);
        
        try {
            Object[] values = (Object[])parameters.evaluate(facts);
            
            Method m = findMethod(target.getClass(), values);
            return m.invoke(target, values);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private Method findMethod(Class<?> targetClass, Object[] values) throws NoSuchMethodException {
        Class<?>[] types= new Class<?>[values.length];
        
        for(int i = 0; i < values.length; i++)
            types[i] = values[i].getClass();
        
        for(Method mtd: targetClass.getMethods()) {
            if(mtd.getName().equals(methodName) && mtd.getParameterCount() == types.length) {
                boolean found = true;
                
                Class<?>[] targetTypes = mtd.getParameterTypes();
                for(int i= 0; i < types.length; i++) {
                    Class<?> type = types[i];
                    Class<?> targetType = targetTypes[i];
                    
                    if(targetType.isAssignableFrom(type))
                        continue;

                    if(targetType.isPrimitive() && Number.class.isAssignableFrom(type))
                        continue;

                    found = false;
                    break;
                }
                
                if(found == true)
                    return mtd;
            }
        }
        
        throw new NoSuchMethodException(targetClass.getName() + "." + methodName + types);
    }
    
    private Object[] getParameters(Class<?>[] targetTypes, Object[] values) {
        Class<?>[] types= new Class<?>[values.length];
        
        for(int i = 0; i < values.length; i++)
            types[i] = values[i].getClass();
        return types;
    }


}
