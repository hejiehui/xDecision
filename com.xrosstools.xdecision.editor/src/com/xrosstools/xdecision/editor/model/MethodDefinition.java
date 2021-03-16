package com.xrosstools.xdecision.editor.model;

import java.util.ArrayList;
import java.util.List;

public class MethodDefinition extends FieldDefinition {
    private List<FieldDefinition> parameters = new ArrayList<FieldDefinition>();
    //For temp use
    private String hints;
    
    public MethodDefinition() {}
    
    public MethodDefinition(String name, String label, DataType returnType, List<FieldDefinition> parameters) {
        setName(name);
        setLabel(label);
        setType(returnType);
        this.parameters = parameters;
    }
    
    public MethodDefinition(String name, String label, DataType returnType, String hints) {
        this(name, label, returnType, new ArrayList<FieldDefinition>());
        this.hints = hints;
    }
    public List<FieldDefinition> getParameters() {
        return parameters;
    }
    public void setParameters(List<FieldDefinition> parameters) {
        this.parameters = parameters;
    }
    public String getHints() {
        return hints;
    }
    public void setHints(String hints) {
        this.hints = hints;
    }    
}
