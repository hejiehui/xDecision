package com.xrosstools.xdecision.editor.model;

public class DataTypeArray extends DataTypeTemplate {
    private static final String LENGTH = "length";

    private FieldDefinition length;
    
    public DataTypeArray(DecisionTreeDiagram diagram) {
        super(diagram, DataTypeEnum.ARRAY);
        length = new FieldDefinition(diagram, LENGTH, DataType.NUMBER_TYPE);
        add(length);
    }
    
    @Override
    public String toString() {
        return getValueType().toString() + "[]";
    }

    public void setValueType(DataType valueType) {
        length.setType(valueType);
        super.setValueType(valueType);
    }
}
