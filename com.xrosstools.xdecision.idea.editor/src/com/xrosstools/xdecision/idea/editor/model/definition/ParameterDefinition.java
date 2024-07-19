package com.xrosstools.xdecision.idea.editor.model.definition;

import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

public class ParameterDefinition extends NamedType {
    public ParameterDefinition(DecisionTreeDiagram diagram, String name) {
        this(diagram, name, DEFAULT_TYPE);
    }
    
    public ParameterDefinition(DecisionTreeDiagram diagram, String name, DataType type) {
        super(diagram, name, NamedElementTypeEnum.PARAMETER, type);
    }
}