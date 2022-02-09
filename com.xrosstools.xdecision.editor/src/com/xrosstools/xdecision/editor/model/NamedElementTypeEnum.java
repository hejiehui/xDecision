package com.xrosstools.xdecision.editor.model;

import java.util.Collections;
import java.util.List;

import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;

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

    public List<DataType> getQualifiedDataTypes(DecisionTreeDiagram diagram) {
        switch (this) {
        case FACTOR:
        case FIELD:
        case METHOD:
        case PARTAMETER:
            return diagram.getAllTypes();
        case DECISION:
        case DATA_TYPE:
            return Collections.emptyList();
        case CONSTANT:
            return DataType.PREDEFINED_TYPES;
        default:
            return null;
        }
    }

    public String getTypeName() {
        return typeName;
    }
    
    public boolean allowCollections() {
        switch (this) {
        case FACTOR:
        case FIELD:
        case METHOD:
        case PARTAMETER:
            return true;
        default:
            return false;
        }
    }
}
