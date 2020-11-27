package com.xrosstools.xdecision.editor.model;

public class FieldDefinition {
    private String name;
    private String label;
    private FactorType type;
    private String customizedType;
    
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
    public FactorType getType() {
        return type;
    }
    public void setType(FactorType type) {
        this.type = type;
    }
    public String getCustomizedType() {
        return customizedType;
    }
    public void setCustomizedType(String customizedType) {
        this.customizedType = customizedType;
    }    
}
