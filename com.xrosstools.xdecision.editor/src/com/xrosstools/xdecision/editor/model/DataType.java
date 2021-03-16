package com.xrosstools.xdecision.editor.model;

public class DataType {
    private DataTypeEnum type;
    private String customizedType;
    
    public DataType(DataTypeEnum type) {
        this.type = type;
    }
    
    public DataType(String customizedType) {
        this(DataTypeEnum.USER_DEFINED);
        this.customizedType = customizedType;
    }

    public DataType(DataTypeEnum type, String customizedType) {
        this(type);
        this.customizedType = customizedType;
    }

    public boolean isCustomized() {
        return type == DataTypeEnum.USER_DEFINED;
    }
    public DataTypeEnum getType() {
        return type;
    }
    public void setType(DataTypeEnum type) {
        this.type = type;
    }
    public String getCustomizedType() {
        return customizedType;
    }
    public void setCustomizedType(String customizedType) {
        this.customizedType = customizedType;
    }
    public boolean iSameType(UserDefinedType udfedType) {
        return isCustomized() && customizedType.equals(udfedType.getName());
    }
}
