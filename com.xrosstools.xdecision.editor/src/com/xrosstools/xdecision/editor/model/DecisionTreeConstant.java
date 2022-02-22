package com.xrosstools.xdecision.editor.model;

import java.util.Date;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class DecisionTreeConstant extends NamedType {
    public DecisionTreeConstant(DecisionTreeDiagram diagram, String name) {
        super(diagram, name, NamedElementTypeEnum.CONSTANT, DEFAULT_TYPE);
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
            break;
        case BOOLEAN:
            setValue("false");
            break;
        case DATE:
            setValue(new Date().toString());
            break;
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
        return combine(super.getPropertyDescriptors(),
                new IPropertyDescriptor[] {new TextPropertyDescriptor(PROP_VALUE, PROP_VALUE)});
    }
    
    public Object getPropertyValue(Object propName) {
        if (PROP_VALUE.equals(propName))
            return value;

        return super.getPropertyValue(propName);
    }

    public void setPropertyValue(Object propName, Object value){
        super.setPropertyValue(propName, value);

        if (PROP_VALUE.equals(propName))
            setValue((String)value);
    }
}
