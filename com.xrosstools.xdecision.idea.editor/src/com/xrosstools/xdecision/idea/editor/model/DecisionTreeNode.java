package com.xrosstools.xdecision.idea.editor.model;

import com.xrosstools.idea.gef.util.ComboBoxPropertyDescriptor;
import com.xrosstools.idea.gef.util.IPropertyDescriptor;
import com.xrosstools.idea.gef.util.IPropertySource;
import com.xrosstools.idea.gef.util.TextPropertyDescriptor;
import com.xrosstools.xdecision.idea.editor.model.definition.PropertyConstants;
import com.xrosstools.xdecision.idea.editor.model.expression.*;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class DecisionTreeNode implements PropertyConstants, IPropertySource, PropertyChangeListener {
	private ExpressionDefinition expression = PlaceholderExpression.EMPTY;

	private DecisionTreeDecision decision;
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

	public ExpressionParser getParser() {
		return manager.getParser();
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
				new TextPropertyDescriptor(PROP_EXPRESSION, PROP_EXPRESSION),
				new ComboBoxPropertyDescriptor(PROP_DECISION, PROP_DECISION, manager.getDecisions().getElementNames()),
				new TextPropertyDescriptor(PROP_DESCRIPTION, PROP_DESCRIPTION),
		};
		return descriptors;
	}

	public Object getPropertyValue(Object propName) {
		if (PROP_EXPRESSION.equals(propName))
			return expression.toString();
		if (PROP_DECISION.equals(propName))
			return getDecisionId();
		if (PROP_DESCRIPTION.equals(propName))
			return getDescription();

		return null;
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_EXPRESSION.equals(propName))
			setNodeExpression(getParser().parseExpression((String)value));
		if (PROP_DECISION.equals(propName)) {
			int index = (Integer)value;
			setDecision(index < 0 ? null : manager.getDecisions().get(index));
		}
		if (PROP_DESCRIPTION.equals(propName))
			setDescription((String) value);
	}

	public Object getEditableValue(){
		return this;
	}

	public boolean isPropertySet(Object propName){
		return true;
	}

	public void resetPropertyValue(Object propName){
	}

	public String getOutlineText() {
		String expDes;
		if(getNodeExpression() == null)
			expDes = "Not specified";
		else
			expDes = getNodeExpression().toString();

		return "[" + (decision == null ? "No decision": decision.getName()) + "] " + expDes;

	}

	public void setNodeExpression(ExpressionDefinition expression) {
		if(this.expression != null)
			this.expression.getListeners().removePropertyChangeListener(this);

		this.expression = expression;

		if(expression != null)
			expression.getListeners().addPropertyChangeListener(this);
		listeners.firePropertyChange(PROP_EXPRESSION, null, expression);
	}

	public ExpressionDefinition getNodeExpression() {
		return expression;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		listeners.firePropertyChange(evt);
	}

	public int getDecisionId() {
		return manager.getDecisions().indexOf(decision);
	}
	public DecisionTreeDecision getDecision() {
		return decision;
	}
	public void setDecision(DecisionTreeDecision decision) {
		this.decision = decision;
		listeners.firePropertyChange(PROP_DECISION, null, decision);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
		listeners.firePropertyChange(PROP_DESCRIPTION, null, description);
	}
	public String getDisplayText(){
		return description == null || description.length() == 0 ? (decision == null ? "" : decision.getName()) : description;
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
