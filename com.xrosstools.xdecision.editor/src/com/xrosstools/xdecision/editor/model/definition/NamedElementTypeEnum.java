package com.xrosstools.xdecision.editor.model.definition;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.editor.commands.definition.DeleteDecisionCommand;
import com.xrosstools.xdecision.editor.commands.definition.DeleteElementCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeConstant;
import com.xrosstools.xdecision.editor.model.DecisionTreeDecision;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;

public enum NamedElementTypeEnum implements DecisionTreeMessages {
    FACTOR(FACTOR_MSG),
    
    DECISION(DECISION_MSG),
    
    DATA_TYPE(TYPE_MSG),
    
    ENUM(ENUM_MSG),
    
    ENUM_VALUE(ENUM_VALUE_MSG),
    
    FIELD(FIELD_MSG),
    
    METHOD(METHOD_MSG),
    
    PARTAMETER(PARAMETER_MSG),
    
    CONSTANT(CONSTANT_MSG),
    
    CONTAINER(CONTAINER_MSG);
    
    private String typeName;
    
    private NamedElementTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public NamedElement newInstance(DecisionTreeDiagram diagram, String name) {
        switch (this) {
        case FACTOR:
            return new DecisionTreeFactor(diagram, name);
        case DECISION:
            return new DecisionTreeDecision(name);
        case DATA_TYPE:
            return new DataType(name);
        case ENUM:
            return new EnumType(name);
        case ENUM_VALUE:
            return new EnumValue(name);
        case FIELD:
            return new FieldDefinition(diagram, name);
        case METHOD:
            return new MethodDefinition(diagram, name);
        case PARTAMETER:
            return new ParameterDefinition(diagram, name);
        case CONSTANT:
            return new DecisionTreeConstant(diagram, name);
        }
        return null;
    }

    private static final String[] NONE = new String[] {};
    public String[] getQualifiedDataTypes(DecisionTreeDiagram diagram) {
        switch (this) {
        case FACTOR:
        case FIELD:
        case METHOD:
        case PARTAMETER:
            return TemplateType.combine(DataType.PREDEFINED_ALL_TYPE_NAMES, diagram.getUserDefinedTypes().getElementNames());
        case DECISION:
        case ENUM:
        case ENUM_VALUE:
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
