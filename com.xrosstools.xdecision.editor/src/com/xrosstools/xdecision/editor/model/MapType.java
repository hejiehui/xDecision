package com.xrosstools.xdecision.editor.model;

public class MapType extends DataType {
    private DataType keyType;
    private DataType valueType;
    public MapType(DataType keyType, DataType valueType) {
        super(String.format("Map<%s, %s>", keyType.getName(), valueType.getName()));
        this.keyType = keyType;
        this.valueType = valueType;
    }
    public DataType getKeyType() {
        return keyType;
    }
    public DataType getValueType() {
        return valueType;
    }
}
