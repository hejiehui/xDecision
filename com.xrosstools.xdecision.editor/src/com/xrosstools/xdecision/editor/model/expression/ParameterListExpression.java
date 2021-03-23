package com.xrosstools.xdecision.editor.model.expression;

import java.util.List;

public class ParameterListExpression extends CompositeExpression {
    public ParameterListExpression() {
        this(0);
    }

    public ParameterListExpression(int parameterCount) {
        for(int i = 0; i < parameterCount; i++)
            addParameter(new PlaceholderExpression());
    }

    public ParameterListExpression(List<ExpressionDefinition> parameters) {
        for(ExpressionDefinition param: parameters)
            addParameter(param);
    }

    public ParameterListExpression addFirst(ParameterExpression parameter) {
        if(!isEmpty())
            ((ParameterExpression)getFirst()).setFirst(false);
        super.addFirst(parameter);
        return this;
    }

    public ParameterListExpression addParameter(ExpressionDefinition parameter) {
        if(size() > 0)
            add(TokenExpression.TOKEN_COMMA);

        add(parameter);
        return this;
    }
    
    public ParameterListExpression setParameter(int index, ExpressionDefinition parameter) {
        set(index * 2, parameter);
        return this;
    }
    
    public String getTypesList() {
        //TODO
        return "";
    }
}
