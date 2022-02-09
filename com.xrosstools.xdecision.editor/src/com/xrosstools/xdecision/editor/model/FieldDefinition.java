package com.xrosstools.xdecision.editor.model;

public class FieldDefinition extends NamedType {
    public FieldDefinition() {
        super(NamedElementTypeEnum.FIELD);
    }

    public FieldDefinition(String name, DataType type) {
        super(name, NamedElementTypeEnum.FIELD, type);
    }    
}
