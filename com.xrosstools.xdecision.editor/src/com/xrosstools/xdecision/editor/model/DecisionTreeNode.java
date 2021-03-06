package com.xrosstools.xdecision.editor.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.ExpressionParser;
import com.xrosstools.xdecision.editor.model.expression.PlaceholderExpression;

public class DecisionTreeNode implements DecisionTreeConstants, IPropertySource {
    private ExpressionParser parser;
    private ExpressionDefinition expression = new PlaceholderExpression();
    private String rawExpression;
	
	private int decisionId = -1;
	private String description;

	private Point location;
	private Dimension size;
	
	private DecisionTreeNodeConnection input;
	private List<DecisionTreeNodeConnection> outputs = new ArrayList<DecisionTreeNodeConnection>();
	
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	private DecisionTreeManager manager;
    private int virtualPos;//Horizontal children count
    private int virtualWidth;//Horizontal children count
	
    public void setDecisionTreeManager(DecisionTreeManager manager){
		this.manager = manager;
	}
	
	public ExpressionParser getParser() {
        return parser;
    }

    public void setParser(ExpressionParser parser) {
        this.parser = parser;
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
				new ComboBoxPropertyDescriptor(PROP_DECISION_ID, PROP_DECISION_ID, manager.getDecisions()),
			};
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_EXPRESSION.equals(propName))
			return expression.toString();
		if (PROP_DECISION_ID.equals(propName))
			return decisionId;

		return null;
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_EXPRESSION.equals(propName))
			setNodeExpression(parser.parse((String)value));
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
	
	public String getOutlineText() {
        String expDes;
        if(getNodeExpression() == null)
            expDes = "Not specified";
        else
            expDes = getNodeExpression().toString();

        
        String decision;
        if(getDecisionId() == -1)
            decision = "No decision";
        else
            decision = manager.getDecision(getDecisionId());
        
        return "[" + decision + "] " + expDes;

	}

	public String getRawExpression() {
        return rawExpression;
    }

    public void setRawExpression(String rawExpression) {
        this.rawExpression = rawExpression;
    }

    public void setNodeExpression(ExpressionDefinition expression) {
	    this.expression = expression;
	    listeners.firePropertyChange(PROP_EXPRESSION, null, expression);
	}

	public ExpressionDefinition getNodeExpression() {
	    return expression;
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
    public int getVirtualPos() {
        return virtualPos;
    }
    public void setVirtualPos(int virtualPos) {
        this.virtualPos = virtualPos;
    }
    public int getVirtualWidth() {
        return virtualWidth;
    }
    public void setVirtualWidth(int virtualWidth) {
        this.virtualWidth = virtualWidth;
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
