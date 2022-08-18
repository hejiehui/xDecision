package com.xrosstools.xdecision.idea.editor.model.definition;


import com.xrosstools.idea.gef.util.IPropertyDescriptor;
import com.xrosstools.idea.gef.util.IPropertySource;
import com.xrosstools.idea.gef.util.PropertyDescriptor;
import com.xrosstools.idea.gef.util.TextPropertyDescriptor;

import java.beans.PropertyChangeSupport;

public class NamedElement implements PropertyConstants, IPropertySource {
    protected static final PropertyDescriptor[] NONE = new PropertyDescriptor[0];
    
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    private NamedElementTypeEnum elementType;
    private String name = "";
    private String propertyName;
    

    public NamedElement(String name, NamedElementTypeEnum type) {
        this.elementType = type;
        this.name = name;
        propertyName = String.format(PROP_NAME_TPL, type.getTypeName());
    }

    public PropertyChangeSupport getListeners() {
        return listeners;
    }

    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        listeners.firePropertyChange(propertyName, oldValue, newValue);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        firePropertyChange(propertyName, null, name);
    }
    
    public NamedElementTypeEnum getElementType() {
        return elementType;
    }
    
    @Override
    public String toString() {
        return getName() != null ? getName() : "-";
    }

    @Override
    public Object getEditableValue() {
        return this;
    }

    protected IPropertyDescriptor getNameDescriptor() {
        return new TextPropertyDescriptor(propertyName);
    }
    
    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        IPropertyDescriptor[] descriptors;
        descriptors = new IPropertyDescriptor[] {
                getNameDescriptor(),
            };
        return descriptors;
    }
    
    protected IPropertyDescriptor[] combine(IPropertyDescriptor[] p1, IPropertyDescriptor[] p2) {
        IPropertyDescriptor[] descriptors = new IPropertyDescriptor[p1.length + p2.length];
        System.arraycopy(p1, 0, descriptors, 0, p1.length);
        System.arraycopy(p2, 0, descriptors, p1.length, p2.length);
        return descriptors;
    }
    
    public Object getPropertyValue(Object propName) {
        if (propertyName.equals(propName))
            return name;

        return null;
    }

    public void setPropertyValue(Object propName, Object value){
        if (propertyName.equals(propName))
            setName((String)value);
    }

    @Override
    public boolean isPropertySet(Object arg0) {
        return true;
    }

    @Override
    public void resetPropertyValue(Object arg0) {
    }
}
