package com.xrosstools.xdecision.editor.model;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.editor.commands.DeleteDecisionCommand;
import com.xrosstools.xdecision.editor.commands.DeleteElementCommand;

public enum NamedElementTypeEnum implements DecisionTreeMessages {
    FACTOR(FACTOR_MSG),
    
    DECISION(DECISION_MSG),
    
    DATA_TYPE(TYPE_MSG),
    
    FIELD(FIELD_MSG),
    
    METHOD(METHOD_MSG),
    
    PARTAMETER(PARAMETER_MSG),
    
    CONSTANT(CONSTANT_MSG),
    
    CONTAINER(CONTAINER_MSG);
    
    private String typeName;
    
    private NamedElementTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public NamedElement newInstance() {
        switch (this) {
        case FACTOR:
            return new DecisionTreeFactor();
        case DECISION:
            return new DecisionTreeDecision();
        case DATA_TYPE:
            return new DataType(DataTypeEnum.USER_DEFINED);
        case FIELD:
            return new FieldDefinition();
        case METHOD:
            return new MethodDefinition();
        case PARTAMETER:
            return new ParameterDefinition();
        case CONSTANT:
            return new DecisionTreeConstant();
        case CONTAINER:
            return new NamedElementContainer<NamedElement>();
        }
        return null;
    }

    private static final String[] NONE = new String[] {};
    public String[] getQualifiedDataTypes() {
        switch (this) {
        case FACTOR:
        case FIELD:
        case METHOD:
        case PARTAMETER:
            return DataType.getAllTypeNames();
        case DECISION:
        case DATA_TYPE:
            return NONE;
        case CONSTANT:
            return DataType.getConstantTypeNames();
        default:
            return null;
        }
    }
    
    public Command createDeleteCommand(DecisionTreeDiagram diagram, NamedElementContainer container, NamedElement element) {
        switch (this) {
        case DECISION:
            return new DeleteDecisionCommand(diagram, (DecisionTreeDecision)element);
        default:
            return new DeleteElementCommand(container, element);
        }
        
    }

    public String getTypeName() {
        return typeName;
    }
}
