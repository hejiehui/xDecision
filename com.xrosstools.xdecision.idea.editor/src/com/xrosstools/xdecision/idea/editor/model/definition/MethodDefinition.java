package com.xrosstools.xdecision.idea.editor.model.definition;

import com.xrosstools.xdecision.idea.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

import java.util.List;

public class MethodDefinition extends NamedType implements DecisionTreeMessages {
    private NamedElementContainer<ParameterDefinition> parameters = new NamedElementContainer<ParameterDefinition>(PARAMETERS_MSG, NamedElementTypeEnum.PARAMETER);
    
    public MethodDefinition(DecisionTreeDiagram diagram, String name) {
        super(diagram, name, NamedElementTypeEnum.METHOD, DEFAULT_TYPE);
    } 
            
    public MethodDefinition(DecisionTreeDiagram diagram, String name, DataType returnType) {
        super(diagram, name, NamedElementTypeEnum.METHOD, returnType);
    }

    public MethodDefinition(DecisionTreeDiagram diagram, String name, DataType returnType, List<ParameterDefinition> parameters) {
        this(diagram, name, returnType);
        this.parameters.setElements(parameters);
    }

    public NamedElementContainer<ParameterDefinition> getParameters() {
        return parameters;
    }
    
    public ParameterDefinition findParameterByName(String name) {
        return (ParameterDefinition)parameters.findByName(name);
    }

    @Override
    public String toString() {
        StringBuilder psb = new StringBuilder();
        String[] names = parameters.getElementNames();
        for (int i = 0; i < names.length; i++) {
            psb.append(names[i]);
            if(i < names.length-1)
                psb.append(", ");
        }
        return getName() + "(" + psb + ")" +  ": " + (getType() != null ? getType().toString() : "-");
    }
}
