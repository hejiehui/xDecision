package com.xrosstools.xdecision.ext;

import java.lang.reflect.Field;

import com.xrosstools.xdecision.Facts;

public class ReferenceExpression extends LeftExpression {
    private String name;

    public ReferenceExpression(String name) {
        super();
        this.name = name;
    }

    @Override
    public Object evaluate(Facts facts) {
        //The first variable
        if(leftExp == null)
            return facts.get(name);

        Object parent = leftExp.evaluate(facts);
        try {
            Field f = parent.getClass().getDeclaredField(name);
            f.setAccessible(true);
            return f.get(parent);
        } catch (Throwable e) {
            throw new IllegalArgumentException("Unable to access field: " + name, e);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
