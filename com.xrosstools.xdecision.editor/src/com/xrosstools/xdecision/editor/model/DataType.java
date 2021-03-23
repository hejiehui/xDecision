package com.xrosstools.xdecision.editor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataType {
    /**
     *     STRING, 
    NUMBER, 
    BOOLEAN, 
    DATE, 
    LIST, 
    SET, 
    MAP, 
    USER_DEFINED, 
    ENUM;
     */

    private String name;
    private String label;
    private List<FieldDefinition> fields = new ArrayList<FieldDefinition>();
    private List<MethodDefinition> methods = new ArrayList<MethodDefinition>();
    
    private static final Map<String, DataType> PREDEFINED_TYPES = new LinkedHashMap<String, DataType>();
    
    public static DataType STRING = new DataType("String") {
        {
            //TODO add static 
            this.getMethods().add(md("valueOf", getName()));
            //length
        }
    };
    
    public static DataType NUMBER = new DataType("Number") {
        {
            this.getMethods().add(md("abs", getName()));
        }
    };
    
    private static void reg(DataType predefined) {
        PREDEFINED_TYPES.put(predefined.getName(), predefined);
    }
    
    private static MethodDefinition md(String name, String returnType) {
        return md(name, returnType, null);
    }

    private static MethodDefinition md(String name, String returnType, String hints) {
        return new MethodDefinition(name, "", returnType, hints);
    }

    static {
        reg(STRING);
        reg(NUMBER);
    }
    
    public DataType(String name) {
        this.name = name;
    }

    public static boolean isCustomized(String typeName) {
        return PREDEFINED_TYPES.containsKey(typeName);
    }
    
    public static Set<String> getPredefinedTypeNames() {
        return PREDEFINED_TYPES.keySet();
    }
    
    public static DataType getPredefinedType(String name) {
        return PREDEFINED_TYPES.get(name);
    }
    
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
