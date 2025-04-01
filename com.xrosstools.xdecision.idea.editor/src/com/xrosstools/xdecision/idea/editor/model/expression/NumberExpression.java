package com.xrosstools.xdecision.idea.editor.model.expression;


import com.xrosstools.idea.gef.util.IPropertyDescriptor;
import com.xrosstools.idea.gef.util.IPropertySource;
import com.xrosstools.idea.gef.util.TextPropertyDescriptor;

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
                new TextPropertyDescriptor(VALUE)
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
