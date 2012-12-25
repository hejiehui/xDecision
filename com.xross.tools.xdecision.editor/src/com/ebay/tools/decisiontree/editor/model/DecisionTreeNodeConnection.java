package com.ebay.tools.decisiontree.editor.model;

import java.beans.PropertyChangeSupport;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public class DecisionTreeNodeConnection implements IPropertySource {
	private int valueId = -1;
	private DecisionTreeNode parent;
	private DecisionTreeNode child;
	
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	public static String FACTOR_VALUE = "Factor value"; 
	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descriptors;
		descriptors = new IPropertyDescriptor[] {
				new ComboBoxPropertyDescriptor(FACTOR_VALUE, FACTOR_VALUE, parent.getDecisionTreeManager().getFactorValues(parent.getFactorId())),
			};

		return descriptors;
	}
	
	public PropertyChangeSupport getListeners() {
		return listeners;
	}

	public Object getPropertyValue(Object propName) {
		if (FACTOR_VALUE.equals(propName))
			return new Integer(valueId);

		return null;
	}

	public void setPropertyValue(Object propName, Object value){
		if (FACTOR_VALUE.equals(propName))
			setValueId((Integer)value);
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
		int oldValueId = -1;
		this.valueId = valueId;
		listeners.firePropertyChange(FACTOR_VALUE, valueId, oldValueId);
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
