package com.xrosstools.xdecision.idea.editor.model.expression;


import com.xrosstools.xdecision.idea.editor.model.definition.NamedElementContainer;
import com.xrosstools.xdecision.idea.editor.model.definition.ParameterDefinition;

public class ParameterListExpression extends CompositeExpression {
    public ParameterListExpression() {
    }

    public ParameterListExpression(NamedElementContainer<ParameterDefinition> parameterDefinitions) {
        for(ParameterDefinition definition: parameterDefinitions.getElements())
            addParameter(new PlaceholderExpression(definition));
    }

    public ParameterListExpression addParameter(ExpressionDefinition parameter) {
        add(new ParameterExpression().setInnerExpression(parameter));
        return this;
    }

    public ParameterListExpression addFirst(ExpressionDefinition parameter) {
        super.addFirst(new ParameterExpression().setInnerExpression(parameter));
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = size()-1;
        for(ExpressionDefinition child: getAllExpression()) {
            sb.append(child.toString());
            if(i-- != 0)
                sb.append(TokenExpression.TOKEN_COMMA);
        }

        return sb.toString();
    }
}
