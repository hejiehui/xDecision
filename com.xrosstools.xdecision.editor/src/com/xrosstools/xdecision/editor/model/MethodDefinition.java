package com.xrosstools.xdecision.editor.model;

import java.util.List;

import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;

public class MethodDefinition extends NamedType implements DecisionTreeMessages {
    private NamedElementContainer<FieldDefinition> parameters = new NamedElementContainer<FieldDefinition>(PARAMETERS_MSG, NamedElementTypeEnum.PARTAMETER);
    
    public MethodDefinition() {
        super(NamedElementTypeEnum.METHOD);
    }
    
    public MethodDefinition(String name, DataType returnType) {
        super(name, NamedElementTypeEnum.METHOD, returnType);
    }
    public MethodDefinition(String name, DataType returnType, List<FieldDefinition> parameters) {
        this(name, returnType);
        this.parameters.setElements(parameters);
    }
    public MethodDefinition(String name, DataType returnType, List<FieldDefinition> parameters, String label) {
        this(name, returnType, parameters);
    }
    public NamedElementContainer<FieldDefinition> getParameters() {
        return parameters;
    }
    
    public FieldDefinition findParameterByName(String name) {
        return (FieldDefinition)parameters.findByName(name);
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
