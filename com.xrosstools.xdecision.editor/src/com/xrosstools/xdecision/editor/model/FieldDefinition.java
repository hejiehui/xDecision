package com.xrosstools.xdecision.editor.model;

import com.xrosstools.xdecision.editor.model.expression.Identifier;

public class FieldDefinition implements Identifier {
    private String name;
    private String label;
    private String typeName;
    private boolean staticMember;
    
    public boolean isStatic() {
        return staticMember;
    }
    public void setStatic(boolean staticMember) {
        this.staticMember = staticMember;
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
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    @Override
    public String getIdentifier() {
        return getName();
    }
}
