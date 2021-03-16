package com.xrosstools.xdecision.editor.model.expression;

import java.beans.PropertyChangeSupport;

public abstract class ExpressionDefinition {
    private ExpressionDefinition parent;
    
    private String COMMON_PROP = "";
    private Object COMMON_OLD_OBJECT = new Object();
    private Object COMMON_NEW_OBJECT = new Object();
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public ExpressionDefinition getParent() {
        return parent;
    }
    
    public void setParent(ExpressionDefinition parent) {
        this.parent = parent;
    }

    public PropertyChangeSupport getListeners() {
        return listeners;
    }

    public void propertyChanged() {
        listeners.firePropertyChange(COMMON_PROP, COMMON_OLD_OBJECT, COMMON_NEW_OBJECT);
    }
    
    @Override
    public String toString() {
        throw new IllegalStateException("Sub class needs to implement this");
    }
}
