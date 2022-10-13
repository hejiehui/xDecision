package com.xrosstools.xdecision.idea.editor.model.definition;

public class EnumType extends DataType {
    public EnumType() {
        super(DataTypeEnum.ENUM);
    }

    public EnumType(String name) {
        super(DataTypeEnum.ENUM);
        setName(name);
    }

    private NamedElementContainer<EnumValue> values = new NamedElementContainer<EnumValue>(ENUM_VALUE_MSG, NamedElementTypeEnum.ENUM_VALUE);
    
    public NamedElementContainer<EnumValue> getValues() {
        return values;
    }

    public EnumValue findByName(String name) {
        return values.findByName(name);
    }
}
