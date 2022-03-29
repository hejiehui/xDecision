package com.xrosstools.xdecision.editor.model.expression;

import com.xrosstools.xdecision.editor.model.MethodDefinition;

public class MethodExpression extends VariableExpression {
    private ParameterListExpression parameters;
    
    public MethodExpression(String name, ParameterListExpression parameters){
        super(name);
        setParameters(parameters);
    }
    
    public MethodExpression(MethodDefinition definition){
        super(definition);
        setParameters(new ParameterListExpression(definition.getParameters()));
    }

    public MethodExpression(ParameterListExpression parameters){
        this("", parameters);
    }
    
    public void setParameters(ParameterListExpression parameters) {
        this.parameters = parameters;
    }

    public ParameterListExpression getParameters() {
        return parameters;
    }
    
    @Override
    public String getIdentifier() {
        return getName() + "(" + parameters.toString() + ")";
    }

    @Override
    public String getMainExpDisplayText() {
        return getName() + "(" + parameters.toString() + ")";
    }
}
