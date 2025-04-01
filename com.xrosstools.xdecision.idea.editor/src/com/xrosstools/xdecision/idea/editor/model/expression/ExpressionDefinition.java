package com.xrosstools.xdecision.idea.editor.model.expression;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class ExpressionDefinition implements PropertyChangeListener {
    private String COMMON_PROP = "";
    private Object COMMON_OLD_OBJECT = new Object();
    private Object COMMON_NEW_OBJECT = new Object();
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public PropertyChangeSupport getListeners() {
        return listeners;
    }

    public void propertyChanged() {
        listeners.firePropertyChange(COMMON_PROP, COMMON_OLD_OBJECT, COMMON_NEW_OBJECT);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        propertyChanged();
    }

    public void listen(ExpressionDefinition expressionDefinition) {
        if(expressionDefinition != null)
            expressionDefinition.getListeners().addPropertyChangeListener(this);
    }

    public void unlisten(ExpressionDefinition expressionDefinition) {
        if(expressionDefinition != null)
            expressionDefinition.getListeners().removePropertyChangeListener(this);
    }

    @Override
    public String toString() {
        throw new IllegalStateException("Sub class needs to implement this");
    }
}
