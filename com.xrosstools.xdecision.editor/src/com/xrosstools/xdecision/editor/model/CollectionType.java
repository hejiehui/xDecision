package com.xrosstools.xdecision.editor.model;

import static java.util.Arrays.asList;

public class CollectionType extends TemplateType {
    public static final String VALUE = "value";
    
    private MethodDefinition size;
    private MethodDefinition isEmpty;
    private MethodDefinition contains;
    private MethodDefinition containsAll;
    
    public CollectionType(DecisionTreeDiagram diagram) {
        this(diagram, DataTypeEnum.COLLECTION);
    }

    public CollectionType(DecisionTreeDiagram diagram, DataTypeEnum type) {
        super(diagram, type);
        
        add(size = new MethodDefinition(diagram, "size", DataType.NUMBER_TYPE));
        add(isEmpty = new MethodDefinition(diagram, "isEmpty", DataType.BOOLEAN_TYPE));
        add(contains = new MethodDefinition(diagram, "contains", DataType.BOOLEAN_TYPE, asList(new ParameterDefinition(diagram, VALUE, getValueType()))));
        add(containsAll = new MethodDefinition(diagram, "containsAll", DataType.BOOLEAN_TYPE, asList(new ParameterDefinition(diagram, VALUE, this))));
    }

    @Override
    public String toString() {
        return getMetaType().getName() + "<" + getValueType().toString() + ">";
    }

    @Override
    public void setValueType(DataType valueType) {
        contains.findParameterByName(VALUE).setType(valueType);
        super.setValueType(valueType);
    }
}