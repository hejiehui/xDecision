package com.xrosstools.xdecision.editor.model;

import static java.util.Arrays.asList;

public class DataTypeList extends DataTypeCollection {
    private MethodDefinition indexOf = new MethodDefinition("indexOf", DataType.NUMBER_TYPE, asList(new FieldDefinition(VALUE, DEFAULT_VALUE_TYPE)));
    private MethodDefinition lastIndexOf = new MethodDefinition("lastIndexOf", DataType.NUMBER_TYPE, asList(new FieldDefinition(VALUE, DEFAULT_VALUE_TYPE)));
    private MethodDefinition get = new MethodDefinition("get", DEFAULT_VALUE_TYPE, asList(new FieldDefinition(VALUE, DataType.NUMBER_TYPE)));
    
    public DataTypeList() {
        super(DataTypeEnum.LIST);

        add(indexOf);
        add(lastIndexOf);
        add(get);
    }

    @Override
    public void setValueType(DataType valueType) {
        indexOf.findParameterByName(VALUE).setType(valueType);
        lastIndexOf.findParameterByName(VALUE).setType(valueType);
        get.setType(valueType);

        super.setValueType(valueType);
    }    
}
