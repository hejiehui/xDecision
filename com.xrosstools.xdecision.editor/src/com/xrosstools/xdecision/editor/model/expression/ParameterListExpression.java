package com.xrosstools.xdecision.editor.model.expression;

import java.util.List;

import com.xrosstools.xdecision.editor.model.NamedElementContainer;
import com.xrosstools.xdecision.editor.model.ParameterDefinition;

public class ParameterListExpression extends CompositeExpression {
    public ParameterListExpression() {
    }

    public ParameterListExpression(NamedElementContainer<ParameterDefinition> parameterDefinitions) {
        for(ParameterDefinition definition: parameterDefinitions.getElements())
            addParameter(new PlaceholderExpression(definition));
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
}
