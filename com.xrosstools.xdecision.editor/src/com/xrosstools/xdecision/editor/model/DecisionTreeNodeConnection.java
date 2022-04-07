package com.xrosstools.xdecision.editor.model;

import java.beans.PropertyChangeSupport;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.PlaceholderExpression;

public class DecisionTreeNodeConnection implements IPropertySource {
	private int valueId = -1;
	private OperatorReference operatorRef = new OperatorReference();
	private ExpressionDefinition expression = new PlaceholderExpression();
	private DecisionTreeNode parent;
	private DecisionTreeNode child;
	
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	public static String EXPRESSION = "Expression";	
	public static String CONDITION = "Condition";
	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descriptors;
		descriptors = new IPropertyDescriptor[] {
		        operatorRef.getPropertyDescriptors()[0],
		        new TextPropertyDescriptor(EXPRESSION, EXPRESSION)
			};

		return descriptors;
	}
	
	public PropertyChangeSupport getListeners() {
		return listeners;
	}

	public ConditionOperator getOperator() {
        return operatorRef.getOperator();
    }

    public void setOperator(ConditionOperator operator) {
        this.operatorRef.setOperator(operator);
    }

    public ExpressionDefinition getExpression() {
        return expression;
    }

    public OperatorReference getOperatorReference() {
        return operatorRef;
    }

    public void setExpression(ExpressionDefinition expression) {
        ExpressionDefinition oldExp = this.expression;
        this.expression = expression;
        listeners.firePropertyChange(EXPRESSION, expression, oldExp);
    }

    public Object getPropertyValue(Object propName) {
		if (EXPRESSION.equals(propName))
		    return expression == null ? "":expression.toString();
		
		if (CONDITION.equals(propName))
		    return operatorRef.getPropertyValue(propName);

		return null;
	}

	public void setPropertyValue(Object propName, Object value){
        if (CONDITION.equals(propName))
            operatorRef.setPropertyValue(propName, value);
		if (EXPRESSION.equals(propName))
		    setExpression(getParent().getParser().parse((String)value));
	}
	
	public Object getEditableValue(){
		return this;
	}

	public boolean isPropertySet(Object propName){
		return true;
	}

	public void resetPropertyValue(Object propName){
	}
	

	public DecisionTreeNodeConnection(DecisionTreeNode parent, DecisionTreeNode child){
		this.parent = parent;
		this.child = child;
		parent.addOutput(this);
		child.setInput(this);
	}

	public int getValueId() {
		return valueId;
	}
	public void setValueId(int valueId) {
		int oldValueId = this.valueId;
		this.valueId = valueId;
		listeners.firePropertyChange(EXPRESSION, valueId, oldValueId);
	}
	public DecisionTreeNode getParent() {
		return parent;
	}
	public void setParent(DecisionTreeNode parent) {
		this.parent = parent;
	}
	public DecisionTreeNode getChild() {
		return child;
	}
	public void setChild(DecisionTreeNode child) {
		this.child = child;
	}
}
