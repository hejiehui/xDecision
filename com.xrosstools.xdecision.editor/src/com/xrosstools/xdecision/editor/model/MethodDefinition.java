package com.xrosstools.xdecision.editor.model;

import java.util.ArrayList;
import java.util.List;

public class MethodDefinition {
    private String name;
    private String label;
    private FactorType returnType;
    private List<FieldDefinition> parameters = new ArrayList<FieldDefinition>();
    //For temp use
    private String hints;
    
    public MethodDefinition(String name, String label, FactorType returnType, List<FieldDefinition> parameters) {
        this.name = name;
        this.label = label;
        this.returnType = returnType;
        this.parameters = parameters;
    }
    
    public MethodDefinition(String name, String label, FactorType returnType, String hints) {
        this(name, label, returnType, new ArrayList<FieldDefinition>());
        this.hints = hints;
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
    public FactorType getReturnType() {
        return returnType;
    }
    public void setReturnType(FactorType returnType) {
        this.returnType = returnType;
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
