package com.xrosstools.xdecision.editor.model;

import java.beans.PropertyChangeSupport;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.ExpressionParser;

public class ExpressionReference implements IPropertySource {
    public static String EXPRESSION = "Expression";

    private ExpressionParser parser;
    private ExpressionDefinition expression;
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    
    private DecisionTreeNodeConnection parent;
    
    public ExpressionDefinition getExpression() {
        return expression;
    }

    public void setExpression(ExpressionDefinition expression) {
        ExpressionDefinition oldExp = this.expression;
        this.expression = expression;
        listeners.firePropertyChange(EXPRESSION, expression, oldExp);
    }

    public ExpressionReference(DecisionTreeNodeConnection parent) {
        this.parent = parent;
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        IPropertyDescriptor[] descriptors;
        descriptors = new IPropertyDescriptor[] {
                new TextPropertyDescriptor(EXPRESSION, EXPRESSION)
            };

        return descriptors;
    }
    
    public PropertyChangeSupport getListeners() {
        return listeners;
    }

    public Object getPropertyValue(Object propName) {
        if (EXPRESSION.equals(propName))
            return expression == null ? "":expression.toString();

        return null;
    }

    public void setPropertyValue(Object propName, Object value){
        if (EXPRESSION.equals(propName))
            setExpression(parent.getParent().getParser().parse((String)value));
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
