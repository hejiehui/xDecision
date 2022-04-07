package com.xrosstools.xdecision.editor.model.expression;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.jdt.internal.core.builder.NameEnvironment;

import com.xrosstools.xdecision.editor.model.NamedElement;

public abstract class ExpressionDefinition implements PropertyChangeListener {
    private String COMMON_PROP = "";
    private Object COMMON_OLD_OBJECT = new Object();
    private Object COMMON_NEW_OBJECT = new Object();
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public PropertyChangeSupport getListeners() {
        return listeners;
    }

    @SuppressWarnings("unchecked")
    public <T> T replaceExpression(ExpressionDefinition oldExp, ExpressionDefinition newExp) {
        if(oldExp != null)
            oldExp.getListeners().removePropertyChangeListener(this);

        if(newExp != null)
            newExp.getListeners().addPropertyChangeListener(this);
        
        propertyChanged();
        return (T)newExp;
    }
    
    @SuppressWarnings("unchecked")
    public <T> T replaceElement(NamedElement oldEle, NamedElement newEle) {
        if(oldEle != null)
            oldEle.getListeners().removePropertyChangeListener(this);

        if(newEle != null)
            newEle.getListeners().addPropertyChangeListener(this);
        
        propertyChanged();
        return (T)newEle;
    }
    
    public void propertyChanged() {
        listeners.firePropertyChange(COMMON_PROP, COMMON_OLD_OBJECT, COMMON_NEW_OBJECT);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        propertyChanged();
    }

    @Override
    public String toString() {
        throw new IllegalStateException("Sub class needs to implement this");
    }
}
