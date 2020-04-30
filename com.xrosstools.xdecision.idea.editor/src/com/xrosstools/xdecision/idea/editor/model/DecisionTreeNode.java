package com.xrosstools.xdecision.idea.editor.model;

import com.xrosstools.gef.util.ComboBoxPropertyDescriptor;
import com.xrosstools.gef.util.IPropertyDescriptor;
import com.xrosstools.gef.util.IPropertySource;

import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class DecisionTreeNode implements DecisionTreeConstants, IPropertySource {
	private int factorId = -1;
	private int decisionId = -1;
	private String description;

	private Point location;
	private Dimension size;
	
	private DecisionTreeNodeConnection input;
	private List<DecisionTreeNodeConnection> outputs = new ArrayList<DecisionTreeNodeConnection>();
	
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	private DecisionTreeManager manager;
	
	public void setDecisionTreeManager(DecisionTreeManager manager){
		this.manager = manager;
	}
	
	public DecisionTreeManager getDecisionTreeManager(){
		return manager;
	}

	public boolean isDecisionTreeManagerSet(){
		return manager != null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descriptors;
		descriptors = new IPropertyDescriptor[] {
				new ComboBoxPropertyDescriptor(PROP_FACTOR_ID, PROP_FACTOR_ID, manager.getFactorNames()),
				new ComboBoxPropertyDescriptor(PROP_DECISION_ID, PROP_DECISION_ID, manager.getDecisions()),
			};
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_FACTOR_ID.equals(propName))
			return factorId;
		if (PROP_DECISION_ID.equals(propName))
			return decisionId;

		return null;
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_FACTOR_ID.equals(propName))
			setFactorId((Integer)value);
		if (PROP_DECISION_ID.equals(propName))
			setDecisionId((Integer)value);
	}
	
	public Object getEditableValue(){
		return this;
	}

	public boolean isPropertySet(Object propName){
		return true;
	}

	public void resetPropertyValue(Object propName){
	}
	
	public int getFactorId() {
		return factorId;
	}
	public void setFactorId(int factorId) {
		this.factorId = factorId;
		listeners.firePropertyChange(PROP_FACTOR_ID, null, factorId);
	}
	public int getDecisionId() {
		return decisionId;
	}
	public void setDecisionId(int decisionId) {
		this.decisionId = decisionId;
		listeners.firePropertyChange(PROP_DECISION_ID, null, decisionId);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Point getLocation() {
		return location;
	}
	public void setLocation(Point location) {
		this.location = location;
		listeners.firePropertyChange(PROP_LOCATION, null, location);
	}
	public Dimension getSize() {
		return size;
	}
	public void setSize(Dimension size) {
		this.size = size;
		listeners.firePropertyChange(PROP_SIZE, null, size);
	}
	public PropertyChangeSupport getListeners() {
		return listeners;
	}
	public DecisionTreeNodeConnection getInput() {
		return input;
	}
	public void setInput(DecisionTreeNodeConnection input) {
		this.input = input;
		listeners.firePropertyChange(PROP_INPUTS, null, input);
	}
	public List<DecisionTreeNodeConnection> getOutputs() {
		return outputs;
	}
	public void addOutput(DecisionTreeNodeConnection output) {
		outputs.add(output);
		listeners.firePropertyChange(PROP_OUTPUTS, null, output);
	}	
	public void removeOutput(DecisionTreeNodeConnection output) {
		outputs.remove(output);
		listeners.firePropertyChange(PROP_OUTPUTS, null, output);
	}	
}
