package com.xrosstools.xdecision.editor.model;

import java.util.ArrayList;
import java.util.List;

public class UserDefinedType {
    private String name;
    private String label;
    private List<FieldDefinition> fields = new ArrayList<FieldDefinition>();
    
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
    public List<FieldDefinition> getFields() {
        return fields;
    }
    public void setFields(List<FieldDefinition> fields) {
        this.fields = fields;
    }
}
