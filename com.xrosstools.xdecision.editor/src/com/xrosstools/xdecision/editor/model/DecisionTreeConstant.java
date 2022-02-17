package com.xrosstools.xdecision.editor.model;

import java.util.Date;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DecisionTreeConstant extends NamedType {
    public DecisionTreeConstant() {
        super(NamedElementTypeEnum.CONSTANT);
    }

    private String value = "";
    
    @Override
    public void setType(DataType type) {
        super.setType(type);

        //Make sure init proper value for type
        switch (type.getType()) {
        case STRING:
            setValue("");
            break;
        case NUMBER:
            setValue("0");
        case BOOLEAN:
            setValue("false");
        case DATE:
            setValue(new Date().toString());
        default:
            setValue("");
            break;
        }
    }
    
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
