package com.xrosstools.xdecision.editor.model.expression;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class NumberExpression extends BasicExpression implements IPropertySource {
    public static final String VALUE = "Value";
    private Number value;

    public NumberExpression(Number value) {
        this.value = value;
    }

    public NumberExpression(String valueText) {
        this.value = parse(valueText);
    }
    
    @Override
    public String getDisplayText() {
        return value.toString();
    }

    public void setValue(Number value) {
        this.value = value;
        propertyChanged();
    }

    public void setValueText(String valueText) {
        value = parse(valueText);
    }
    
    public Number parse(String valueText) {
        if(valueText.contains("."))
            return new Double(valueText);
        else
            return new Integer(valueText);
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        IPropertyDescriptor[] descriptors;
        descriptors = new IPropertyDescriptor[] {
                new TextPropertyDescriptor(VALUE, VALUE)
            };
        return descriptors;
    }
    
    public Object getPropertyValue(Object propName) {
        if (VALUE.equals(propName))
            return value.toString();

        return null;
    }

    public void setPropertyValue(Object propName, Object value){
        if (VALUE.equals(propName))
            setValue(parse((String)value));
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
