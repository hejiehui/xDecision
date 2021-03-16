package com.xrosstools.xdecision.editor.model;

import java.util.List;

public enum DataTypeEnum {
    STRING, 
    NUMBER, 
    BOOLEAN, 
    DATE, 
    LIST, 
    SET, 
    MAP, 
    USER_DEFINED, 
    ENUM;
    
    public static DataTypeEnum[] getDisplayTypes() {
        return new DataTypeEnum[] {STRING, NUMBER, BOOLEAN, DATE, LIST, SET, MAP};
    }
    
    public MethodDefinition[] getStaticMethods() {
        switch (this) {
        case STRING:
            return new MethodDefinition[] {md("valueOf", STRING)};
        case NUMBER:
            return new MethodDefinition[] {md("abs", NUMBER)};
        default:
            return new MethodDefinition[0];
        }
    }
    
    public MethodDefinition[] getInstanceMethods() {
        switch (this) {
        case STRING:
            return new MethodDefinition[] {md("length", STRING)};
        default:
            return new MethodDefinition[0];
        }
    }
    
    private static MethodDefinition md(String name, DataTypeEnum returnType) {
        return md(name, returnType, null);
    }

    private static MethodDefinition md(String name, DataTypeEnum returnType, String hints) {
        return new MethodDefinition(name, "", new DataType(returnType), hints);
    }
}
