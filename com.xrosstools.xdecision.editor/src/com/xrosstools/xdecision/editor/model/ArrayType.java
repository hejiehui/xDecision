package com.xrosstools.xdecision.editor.model;

public class ArrayType extends TemplateType {
    private static final String LENGTH = "length";

    private FieldDefinition length;
    
    public ArrayType(DecisionTreeDiagram diagram) {
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
