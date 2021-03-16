package com.xrosstools.xdecision.editor.model;

public class FieldDefinition {
    private String name;
    private String label;
    private DataType type;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public DataType getType() {
        return type;
    }
    public void setType(DataType type) {
        this.type = type;
    }
}
