package com.xrosstools.xdecision.editor.model;

import java.util.ArrayList;
import java.util.List;

public class UserDefinedType {
    private String name;
    private String label;
    private List<FieldDefinition> fields = new ArrayList<FieldDefinition>();
    private List<MethodDefinition> methods = new ArrayList<MethodDefinition>();
    
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
    public FieldDefinition findField(String fieldName) {
        for(FieldDefinition f: fields) {
            if(f.getName().equals(fieldName))
                return f;
        }
        return null;
    }
    public List<MethodDefinition> getMethods() {
        return methods;
    }
    public void setMethods(List<MethodDefinition> methods) {
        this.methods = methods;
    }
    public MethodDefinition findMethod(String methodName) {
        for(MethodDefinition f: methods) {
            if(f.getName().equals(methodName))
                return f;
        }
        return null;
    }
}
