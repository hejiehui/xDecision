package com.xrosstools.xdecision.editor.model;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DecisionTreeConstant extends NamedType {
    public DecisionTreeConstant() {
        super(NamedElementTypeEnum.CONSTANT);
    }

    //TODO We don't validate format of the value against data type for now
    private String value = "";
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        firePropertyChange(PROP_VALUE, null, value);
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        IPropertyDescriptor[] descriptors;
        descriptors = new IPropertyDescriptor[] {
                new TextPropertyDescriptor(PROP_VALUE, PROP_VALUE),
            };
        return descriptors;
    }
    
    public Object getPropertyValue(Object propName) {
        if (PROP_VALUE.equals(propName))
            return value;

        return null;
    }

    public void setPropertyValue(Object propName, Object value){
        if (PROP_VALUE.equals(propName))
            setValue((String)value);
    }
}
