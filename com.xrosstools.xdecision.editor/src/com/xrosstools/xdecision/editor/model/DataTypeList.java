package com.xrosstools.xdecision.editor.model;

import static java.util.Arrays.asList;

public class DataTypeList extends DataTypeCollection {
    private MethodDefinition indexOf;
    private MethodDefinition lastIndexOf;
    private MethodDefinition get;
    
    public DataTypeList(DecisionTreeDiagram diagram) {
        super(diagram, DataTypeEnum.LIST);

        add(indexOf = new MethodDefinition(diagram, "indexOf", DataType.NUMBER_TYPE, asList(new ParameterDefinition(diagram, VALUE, DEFAULT_VALUE_TYPE))));
        add(lastIndexOf = new MethodDefinition(diagram, "lastIndexOf", DataType.NUMBER_TYPE, asList(new ParameterDefinition(diagram, VALUE, DEFAULT_VALUE_TYPE))));
        add(get = new MethodDefinition(diagram, "get", DEFAULT_VALUE_TYPE, asList(new ParameterDefinition(diagram, VALUE, DataType.NUMBER_TYPE))));
    }

    @Override
    public void setValueType(DataType valueType) {
        indexOf.findParameterByName(VALUE).setType(valueType);
        lastIndexOf.findParameterByName(VALUE).setType(valueType);
        get.setType(valueType);

        super.setValueType(valueType);
    }    
}
