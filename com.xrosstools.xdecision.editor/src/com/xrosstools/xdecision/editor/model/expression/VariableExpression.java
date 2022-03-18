package com.xrosstools.xdecision.editor.model.expression;

import com.xrosstools.xdecision.editor.model.NamedElement;
import com.xrosstools.xdecision.editor.model.NamedType;

public class VariableExpression extends ExtensibleExpression implements Identifier {
    private String name;
    private NamedType referenceType;

    public VariableExpression(String name) {
        this.name = name;
    }
    
    public VariableExpression(NamedType referenceType) {
        this.referenceType = referenceType;
    }
    
    public String getIdentifier() {
        return getName();
    }

    public String getName() {
        return referenceType == null ? name : referenceType.getName();
    }

    public void setName(String name) {
        this.name = name;
        propertyChanged();
    }
    
    public String getMainExpDisplayText() {
        return getIdentifier();
    }

    public boolean isValid() {
        return referenceType != null;
    }
    
    public NamedType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(NamedType member) {
        this.referenceType = member;
        propertyChanged();
    }
}
