package com.xrosstools.xdecision.editor.model;

public class ParameterDefinition extends NamedType {
    public ParameterDefinition() {
        super(NamedElementTypeEnum.PARTAMETER);
    }

    public ParameterDefinition(String name, DataType type) {
        super(name, NamedElementTypeEnum.PARTAMETER, type);
    }    
}