package com.xrosstools.xdecision.editor.model;

import java.beans.PropertyChangeSupport;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public class OperatorReference implements IPropertySource {
    public static String CONDITION = "Condition";

    private ConditionOperator operator;
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    
    
    public ConditionOperator getOperator() {
        return operator;
    }

    public void setOperator(ConditionOperator operator) {
        ConditionOperator oldOpr = this.operator;
        this.operator = operator;
        listeners.firePropertyChange(CONDITION, operator, oldOpr);        
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        IPropertyDescriptor[] descriptors;
        descriptors = new IPropertyDescriptor[] {
                new ComboBoxPropertyDescriptor(CONDITION, CONDITION, ConditionOperator.getAllOperatorText()),
            };

        return descriptors;
    }
    
    public PropertyChangeSupport getListeners() {
        return listeners;
    }

    public Object getPropertyValue(Object propName) {
        if (CONDITION.equals(propName))
            return operator == null? -1:operator.ordinal();

        return null;
    }

    public void setPropertyValue(Object propName, Object value){
        if (CONDITION.equals(propName))
            setOperator(ConditionOperator.values()[(Integer)value]);
    }
    
    public Object getEditableValue(){
        return this;
    }

    public boolean isPropertySet(Object propName){
        return true;
    }

    public void resetPropertyValue(Object propName){
    }

}
