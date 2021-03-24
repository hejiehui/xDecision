package com.xrosstools.xdecision.editor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataType {
    public static final String STRING = "String"; 
    public static final String NUMBER = "Number";
    public static final String BOOLEAN = "Boolean";
    public static final String DATE = "Date";
    public static final String LIST = "List";
    public static final String SET = "Set";
    public static final String MAP = "Map";
    public static final String ENUM = "Enum";
    
    private static final Map<String, DataType> PREDEFINED_TYPES = new LinkedHashMap<String, DataType>();

    private static final FieldDefinition[] NO_FIELD = new FieldDefinition[0];
    private static final MethodDefinition[] NO_METHOD = new MethodDefinition[0];

    private String name;
    private String label;
    private List<FieldDefinition> fields = new ArrayList<FieldDefinition>();
    private List<MethodDefinition> methods = new ArrayList<MethodDefinition>();
    
    
    static {
        reg(STRING, NO_FIELD, new MethodDefinition[]{
                staticMtd("valueOf", STRING),
                mtd("length", NUMBER),
        });

        reg(NUMBER, NO_FIELD, new MethodDefinition[]{
                staticMtd("valueOf", NUMBER),
                mtd("abs", NUMBER),
        });

        reg(BOOLEAN, NO_FIELD, new MethodDefinition[]{
                staticMtd("valueOf", BOOLEAN),
        });
        
        reg(DATE, NO_FIELD, new MethodDefinition[]{
                staticMtd("valueOf", DATE),
        });
        
        reg(ENUM, NO_FIELD, NO_METHOD);        
    }
    
    private static void reg(String name, FieldDefinition[] fields, MethodDefinition[] methods) {
        DataType type = new DataType(name);
        type.fields = Arrays.asList(fields);
        type.methods = Arrays.asList(methods);
        PREDEFINED_TYPES.put(name, type);
    }

    public DataType(String name) {
        this.name = name;
    }
    
    protected void init() {}

    protected void add(MethodDefinition method) {
        methods.add(method);
    }
    
    protected void add(FieldDefinition field) {
        fields.add(field);
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
    
    public boolean matches(String typeName) {
        return name.equals(typeName);
    }

    public boolean isCompositeType() {
        return false;
    }

    public String getKeyType(String typeName) {
        return null;
    }

    public String getValueType(String typeName) {
        return null;
    }

    public List<FieldDefinition> getFields() {
        return fields;
    }
    public void setFields(List<FieldDefinition> fields) {
        this.fields = fields;
    }

    public List<MethodDefinition> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodDefinition> methods) {
        this.methods = methods;
    }

    public FieldDefinition findField(String fieldName) {
        for(FieldDefinition f: fields) {
            if(f.getName().equals(fieldName))
                return f;
        }
        return null;
    }

    public MethodDefinition findMethod(String methodName) {
        for(MethodDefinition f: methods) {
            if(f.getName().equals(methodName))
                return f;
        }
        return null;
    }
    
    public static MethodDefinition mtd(String name, String returnType) {
        return md(name, returnType, null, false);
    }

    public static MethodDefinition staticMtd(String name, String returnType) {
        return md(name, returnType, null, true);
    }

    public static MethodDefinition md(String name, String returnType, String hints, boolean isStatic) {
        MethodDefinition md = new MethodDefinition(name, "", returnType, hints);
        md.setStatic(isStatic);
        return md;
    }
}
