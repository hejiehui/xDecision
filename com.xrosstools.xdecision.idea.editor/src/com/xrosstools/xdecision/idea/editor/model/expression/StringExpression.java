package com.xrosstools.xdecision.idea.editor.model.expression;


import com.xrosstools.idea.gef.util.IPropertyDescriptor;
import com.xrosstools.idea.gef.util.IPropertySource;
import com.xrosstools.idea.gef.util.TextPropertyDescriptor;

public class StringExpression extends BasicExpression implements IPropertySource {
    public static final String TEXT = "Text";

    private String text;

    public StringExpression(String text) {
        super();
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        propertyChanged();
    }

    @Override
    public String getDisplayText() {
        return "'" + text + "'";
    }
    
    public IPropertyDescriptor[] getPropertyDescriptors() {
        IPropertyDescriptor[] descriptors;
        descriptors = new IPropertyDescriptor[] {
                new TextPropertyDescriptor(TEXT)
            };
        return descriptors;
    }
    
    public Object getPropertyValue(Object propName) {
        if (TEXT.equals(propName))
            return text;

        return null;
    }

    public void setPropertyValue(Object propName, Object value){
        if (TEXT.equals(propName))
            setText((String)value);
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
