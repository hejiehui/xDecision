package com.xrosstools.xdecision.editor.model.expression;

import com.xrosstools.xdecision.editor.model.NamedElement;
import com.xrosstools.xdecision.editor.model.NamedType;

public class VariableExpression extends ExtensibleExpression implements Identifier {
    private String name;
    private NamedElement referenceElement;

    public VariableExpression(String name) {
        this.name = name;
    }
    
    public VariableExpression(NamedElement referenceType) {
        this.referenceElement = referenceType;
    }
    
    public String getIdentifier() {
        return getName();
    }

    public String getName() {
        return referenceElement == null ? name : referenceElement.getName();
    }

    public void setName(String name) {
        this.name = name;
        propertyChanged();
    }
    
    public String getMainExpDisplayText() {
        return getIdentifier();
    }

    public boolean isValid() {
        return referenceElement != null;
    }
    
    public NamedElement getReferenceElement() {
        return referenceElement;
    }

    public void setReferenceElement(NamedElement member) {
        this.referenceElement = member;
        propertyChanged();
    }
}
