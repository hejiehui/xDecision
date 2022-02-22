package com.xrosstools.xdecision.editor.model;

public class ParameterDefinition extends NamedType {
    public ParameterDefinition(DecisionTreeDiagram diagram, String name) {
        this(diagram, name, DEFAULT_TYPE);
    }
    
    public ParameterDefinition(DecisionTreeDiagram diagram, String name, DataType type) {
        super(diagram, name, NamedElementTypeEnum.PARTAMETER, type);
    }
}