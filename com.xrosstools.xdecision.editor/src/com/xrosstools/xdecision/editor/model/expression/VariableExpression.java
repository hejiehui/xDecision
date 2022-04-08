package com.xrosstools.xdecision.editor.model.expression;

import com.xrosstools.xdecision.editor.model.definition.NamedElement;

public class VariableExpression extends ExtensibleExpression {
    private String name;
    private NamedElement referenceElement;

    public VariableExpression(String name) {
        setName(name);
    }
    
    public VariableExpression(NamedElement referenceElement) {
        setReferenceElement(referenceElement);
    }

    public String getName() {
        return referenceElement == null ? name : referenceElement.getName();
    }

    public void setName(String name) {
        this.name = name;
        propertyChanged();
    }
    
    public boolean isValid() {
        return referenceElement != null;
    }
    
    public NamedElement getReferenceElement() {
        return referenceElement;
    }

    public void setReferenceElement(NamedElement newReferenceElement) {
        this.referenceElement = newReferenceElement;
        this.referenceElement.getListeners().addPropertyChangeListener(this);
        propertyChanged();
    }

    @Override
    public String getBaseString() {
        return getName();
    }
}
