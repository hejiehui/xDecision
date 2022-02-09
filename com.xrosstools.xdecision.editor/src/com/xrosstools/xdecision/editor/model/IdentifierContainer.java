package com.xrosstools.xdecision.editor.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import com.xrosstools.xdecision.editor.model.expression.Identifier;

public class IdentifierContainer<T extends Identifier> implements Identifier, PropertyConstants {
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    
    private String identifier;
    private NamedElementTypeEnum elementType;
    private List<T> elements = new ArrayList<T>();
    
    public PropertyChangeSupport getListeners() {
        return listeners;
    }
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        listeners.firePropertyChange(propertyName, oldValue, newValue);
    }
    
    public IdentifierContainer() {}
    
    public IdentifierContainer(String identifier, NamedElementTypeEnum elementType) {
        this.identifier = identifier;
        this.elementType = elementType;
    }

    @Override
    public String toString() {
        return getIdentifier();
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
        firePropertyChange(PROP_IDENTIFIER, null, identifier);
    }
    
    public NamedElementTypeEnum getElementType() {
        return elementType;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }
    
    public void add(T element) {
        elements.add(element);
    }

    public void remove(NamedType element) {
        elements.remove(element);
    }
    
    public T findByName(String name) {
        for(T type: elements) {
            if(type.getIdentifier().equals(name))
                return type;
        }
        
        //Shall we return NULL Object?
        return null;
    }
}
