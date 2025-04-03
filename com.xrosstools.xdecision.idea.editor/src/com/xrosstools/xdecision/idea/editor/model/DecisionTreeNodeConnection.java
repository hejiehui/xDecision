package com.xrosstools.xdecision.idea.editor.model;


import com.xrosstools.idea.gef.util.ComboBoxPropertyDescriptor;
import com.xrosstools.idea.gef.util.IPropertyDescriptor;
import com.xrosstools.idea.gef.util.IPropertySource;
import com.xrosstools.idea.gef.util.TextPropertyDescriptor;
import com.xrosstools.xdecision.idea.editor.model.definition.PropertyConstants;
import com.xrosstools.xdecision.idea.editor.model.expression.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DecisionTreeNodeConnection implements PropertyConstants, IPropertySource, PropertyChangeListener {
	private int valueId = -1;
	private ConditionOperator operator = ConditionOperator.EQUAL;
	private ExpressionDefinition expression = new NumberExpression(0);
	private DecisionTreeNode parent;
	private DecisionTreeNode child;

	private int actualWidth;

	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] {
				new ComboBoxPropertyDescriptor(PROP_CONDITION, PROP_CONDITION, ConditionOperator.getAllOperatorText()),
				new TextPropertyDescriptor(PROP_EXPRESSION, PROP_EXPRESSION)
		};
	}

	public PropertyChangeSupport getListeners() {
		return listeners;
	}

	public void setOperator(ConditionOperator operator) {
		this.operator = operator;
		listeners.firePropertyChange(PROP_CONDITION, null, operator);
	}

	public ConditionOperator getOperator() {
		return operator;
	}

	public ExpressionDefinition getExpression() {
		return expression;
	}

	public void setExpression(ExpressionDefinition expression) {
		if(this.expression != null)
			this.expression.getListeners().removePropertyChangeListener(this);

		this.expression = expression;

		if(expression != null)
			expression.getListeners().addPropertyChangeListener(this);
		listeners.firePropertyChange(PROP_EXPRESSION, null, expression);
	}

	public int getActualWidth() {
		return actualWidth;
	}

	public void setActualWidth(int actualWidth) {
		this.actualWidth = actualWidth;
	}

	public Object getPropertyValue(Object propName) {
		if (PROP_EXPRESSION.equals(propName))
			return expression == null ? "" : expression.toString();

		if (PROP_CONDITION.equals(propName))
			return operator == null? -1 : operator.ordinal();

		return null;
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_CONDITION.equals(propName))
			setOperator(ConditionOperator.values()[(Integer)value]);
		if (PROP_EXPRESSION.equals(propName)) {
			parseExpression(getParent().getParser(), (String)value);
		}
	}

	public void parseExpression(ExpressionParser parser, String rawExpression) {
		setExpression(parser.parseParameters(ExpressionType.P, rawExpression));
	}

	public Object getEditableValue(){
		return this;
	}

	public boolean isPropertySet(Object propName){
		return true;
	}

	public void resetPropertyValue(Object propName){
	}
	public DecisionTreeNodeConnection() {}

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
		listeners.firePropertyChange(PROP_EXPRESSION, valueId, oldValueId);
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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		listeners.firePropertyChange(evt);
	}

	public void layout() {
		listeners.firePropertyChange(PROP_CONDITION, null, operator);
	}
}
