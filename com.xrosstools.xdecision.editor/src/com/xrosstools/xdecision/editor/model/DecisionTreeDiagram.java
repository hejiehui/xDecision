package com.xrosstools.xdecision.editor.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public class DecisionTreeDiagram implements IPropertySource {
	private String description;
	private String factorDescription;
	private List<DecisionTreeFactor> factors = new ArrayList<DecisionTreeFactor>();
	private String decisionDescription;
	private List<String> decisions= new ArrayList<String>();
	private List<UserDefinedType> userDefinedTypes = new ArrayList<UserDefinedType>();
	private UserDefinedType type = new UserDefinedType();
	
    private String parserClass;
	private String evaluatorClass;

	private List<DecisionTreeNode> nodes = new ArrayList<DecisionTreeNode>();;
	private List<DecisionTreeRoot> roots = new ArrayList<DecisionTreeRoot>();

	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	private boolean isHorizantal;
	private int verticalSpace = 50;
	private int horizantalSpace = 50;
	private float alignment = 0;
	private int nodeWidth = 100;
	private int nodeHeight = 50;
	
	public static final String LAYOUT = "layout";
	public static final String NODE = "node";
	
	private DecisionTreeDiagramPropertySource source = new DecisionTreeDiagramPropertySource(this);
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return source.getPropertyDescriptors();
	}
	
	public Object getPropertyValue(Object propName) {
		return source.getPropertyValue(propName);
	}

	public void setPropertyValue(Object id, Object valueObj){
		source.setPropertyValue(id, valueObj);
		listeners.firePropertyChange(LAYOUT, null, null);
	}
	
	public Object getEditableValue(){
		return this;
	}

	public boolean isPropertySet(Object propName){
		return source.isPropertySet(propName);
	}

	public void resetPropertyValue(Object propName){
		source.resetPropertyValue(propName);
	}

	public String getFactorDescription() {
		return factorDescription;
	}
	public void setFactorDescription(String factorDescription) {
		this.factorDescription = factorDescription;
	}
	public String getDecisionDescription() {
		return decisionDescription;
	}
	public String getParserClass() {
        return parserClass;
    }
    public void setParserClass(String parserClass) {
        this.parserClass = parserClass;
    }
    public String getEvaluatorClass() {
        return evaluatorClass;
    }
    public void setEvaluatorClass(String evaluatorClass) {
        this.evaluatorClass = evaluatorClass;
    }
    public void setDecisionDescription(String decisionDescription) {
		this.decisionDescription = decisionDescription;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<DecisionTreeFactor> getFactors() {
		return factors;
	}
	public void setFactors(List<DecisionTreeFactor> factors) {
		this.factors = factors;
	}
	public DecisionTreeFactor getFactorById(int index) {
	    return factors.get(index);
	}
	public int indexOf(DecisionTreeFactor factor) {
	    return factors.indexOf(factor);
	}
	public List<String> getDecisions() {
		return decisions;
	}
	public void setDecisions(List<String> decisions) {
		this.decisions = decisions;
	}
    public UserDefinedType getType() {
        return type;
    }
    public void setType(UserDefinedType type) {
        this.type = type;
    }
    public List<UserDefinedType> getUserDefinedTypes() {
        return userDefinedTypes;
    }
    public void setUserDefinedTypes(List<UserDefinedType> userDefinedTypes) {
        this.userDefinedTypes = userDefinedTypes;
    }
    public DataType getFactorType(int factorId) {
        return getFactorById(factorId).getType();
    }
    public DataType getFactorType(String factorName) {
        return getFactorType(getFactorId(factorName));
    }
    public int getFactorId(String factorName){
        List<DecisionTreeFactor> factors = getFactors();
        for(int i = 0; i < factors.size(); i++)
            if(factors.get(i).getFactorName().equals(factorName))
                return i;
        return -1;
    }
    public UserDefinedType findUserDefinedType(String name) {
        for(UserDefinedType type: userDefinedTypes) {
            if(type.getName().equals(name))
                return type;
        }
        return null;
    }
	public List<DecisionTreeNode> getNodes() {
		return nodes;
	}
    public void setNodes(List<DecisionTreeNode> nodes) {
        this.nodes = nodes;
    }
	public void addNode(DecisionTreeNode node) {
		nodes.add(node);
		listeners.firePropertyChange(NODE, null, null);
	}
	public void removeNode(DecisionTreeNode node) {
		nodes.remove(node);
		listeners.firePropertyChange(NODE, null, null);
	}
	public List<DecisionTreeRoot> getRoots() {
		return roots;
	}
	public void setRoots(List<DecisionTreeRoot> roots) {
		this.roots = roots;
	}

	public int getVerticalSpace() {
		return verticalSpace;
	}

	public void setVerticalSpace(int verticalSpace) {
		this.verticalSpace = verticalSpace;
		fireLayoutChange();
	}

	public int getHorizantalSpace() {
		return horizantalSpace;
	}

	public void setHorizantalSpace(int horizantalSpace) {
		this.horizantalSpace = horizantalSpace;
		fireLayoutChange();
	}

	public float getAlignment() {
		return alignment;
	}

	public void setAlignment(float alignment) {
		this.alignment = alignment;
		fireLayoutChange();
	}

	public int getNodeWidth() {
		return nodeWidth;
	}

	public void setNodeWidth(int nodeWidth) {
		this.nodeWidth = nodeWidth;
		fireLayoutChange();
	}

	public int getNodeHeight() {
		return nodeHeight;
	}

	public void setNodeHeight(int nodeHeight) {
		this.nodeHeight = nodeHeight;
		fireLayoutChange();
	}

	public boolean isHorizantal() {
		return isHorizantal;
	}

	public void setHorizantal(boolean isHorizantal) {
		this.isHorizantal = isHorizantal;
		fireLayoutChange();
	}

	public PropertyChangeSupport getListeners() {
		return listeners;
	}
	
	private void fireLayoutChange(){
		listeners.firePropertyChange(LAYOUT, null, null);
	}
}
