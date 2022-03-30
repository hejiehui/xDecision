package com.xrosstools.xdecision.editor.model.expression;

import com.xrosstools.xdecision.editor.model.NamedElementContainer;
import com.xrosstools.xdecision.editor.model.ParameterDefinition;

public class ParameterListExpression extends CompositeExpression {
    public ParameterListExpression() {
    }

    public ParameterListExpression(NamedElementContainer<ParameterDefinition> parameterDefinitions) {
        for(ParameterDefinition definition: parameterDefinitions.getElements())
            addParameter(new PlaceholderExpression(definition));
    }

    public ParameterListExpression addParameter(ExpressionDefinition parameter) {
        if(size() > 0)
            add(TokenExpression.TOKEN_COMMA);

        add(parameter);
        return this;
    }

    public ParameterListExpression addFirst(ExpressionDefinition parameter) {
        if(size() > 0)
            super.addFirst(TokenExpression.TOKEN_COMMA);

        super.addFirst(parameter);
        return this;
    }
}
