package com.xrosstools.xdecision.editor.model.expression;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.xrosstools.xdecision.editor.model.NamedElement;

public class VariableExpression extends ExtensibleExpression implements Identifier, PropertyChangeListener {
    private String name;
    private NamedElement referenceElement;

    public VariableExpression(String name) {
        this.name = name;
    }
    
    public VariableExpression(NamedElement referenceElement) {
        setReferenceElement(referenceElement);
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

    public void setReferenceElement(NamedElement referenceElement) {
        this.referenceElement = referenceElement;
        referenceElement.getListeners().addPropertyChangeListener(this);
        propertyChanged();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        propertyChanged();
    }
}
