package com.xrosstools.xdecision.idea.editor.model;

import com.xrosstools.gef.util.IPropertyDescriptor;
import com.xrosstools.gef.util.IPropertySource;
import com.xrosstools.gef.util.PropertyDescriptor;
import com.xrosstools.gef.util.TextPropertyDescriptor;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class DecisionTreeDiagramPropertySource implements IPropertySource {
	public static final String NAME = "Name";
	public static final String VALUE_ = "Value ";
	public static final String COMMENTS = "Comments";
	public static final String CONFIGURE = "Configure";
	public static final String OVERVIEW = "Overview";
	public static final String SPACE = " ";
	public static final String FACTOR_COMMENTS = "Factor comments";
	public static final String FACTOR = "Factor ";
	public static final String _VALUE_ = " value ";
	public static final String DECISION_COMMENTS = "Decision comments";
	public static final String DECISION = "Decision ";
	public static final String LAYOUT = "layout";
	public static final String NODE = "node";
    public static final String PARSER = "Parser";
    public static final String EVALUATOR = "Evaluator";
	
	private DecisionTreeDiagram diagram;
	public DecisionTreeDiagramPropertySource(DecisionTreeDiagram diagram){
		this.diagram = diagram;
	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> props = new ArrayList<IPropertyDescriptor>();
		//new TextPropertyDescriptor(NAME, NAME),
        PropertyDescriptor desc = new TextPropertyDescriptor( COMMENTS);
        desc.setCategory(CONFIGURE);
        props.add(desc);

        desc = new TextPropertyDescriptor(PARSER);
        desc.setCategory(CONFIGURE);
        props.add(desc);

        desc = new TextPropertyDescriptor(EVALUATOR);
        desc.setCategory(CONFIGURE);
        props.add(desc);
		
		for(int i = 0; i < diagram.getFactors().size(); i++){
			desc = new TextPropertyDescriptor(FACTOR + i);
			desc.setCategory("Factor " + i);
			props.add(desc);
			DecisionTreeFactor factor = diagram.getFactors().get(i);
			if(factor.getFactorValues() == null)
				factor.setFactorValues(new String[]{"value 0", "value 1"});
			for(int j = 0; j < factor.getFactorValues().length; j++ ){
				desc = new TextPropertyDescriptor(FACTOR + i + _VALUE_ + j);
				desc.setCategory("Factor " + i);
				props.add(desc);
			}
		}

		for(int i = 0; i < diagram.getDecisions().size(); i++){
			desc = new TextPropertyDescriptor(DECISION + i);
			desc.setCategory("Decisions");
			props.add(desc);
		}

		return props.toArray(new IPropertyDescriptor[0]);
	}
	
	public Object getPropertyValue(Object propName) {
		String prop = (String)propName;
		
		if (COMMENTS.equals(prop))
			return diagram.getDescription();

        if(prop.equals(PARSER))
            return diagram.getParserClass();

        if(prop.equals(EVALUATOR))
            return diagram.getEvaluatorClass();

		if(prop.startsWith(FACTOR) && !prop.contains(_VALUE_)){
			int index = Integer.parseInt(prop.substring(prop.indexOf(SPACE) + 1));
			return diagram.getFactors().get(index).getFactorName();
		}
		
		if(prop.startsWith(FACTOR) && prop.contains(_VALUE_)){
			int firstSpace = prop.indexOf(SPACE) + 1;
			int secondSpace = prop.indexOf(SPACE, firstSpace);
			int factorId = Integer.parseInt(prop.substring(firstSpace, secondSpace));
			int valueId = Integer.parseInt(prop.substring(prop.lastIndexOf(SPACE) + 1));
			return diagram.getFactors().get(factorId).getFactorValues()[valueId];
		}

		if(prop.startsWith(DECISION)){
			int index = Integer.parseInt(prop.substring(prop.indexOf(SPACE) + 1));
			return diagram.getDecisions().get(index);
		}

		return null;
	}

	public void setPropertyValue(Object id, Object valueObj){
		String prop = (String)id;
		String value = (String)valueObj;
		
		if (COMMENTS.equals(id))
			diagram.setDescription((String)value);

        if (PARSER.equals(prop))
            diagram.setParserClass(value);

        if (EVALUATOR.equals(prop))
            diagram.setEvaluatorClass(value);
		
		if(prop.startsWith(FACTOR) && !prop.contains(_VALUE_)){
			int index = Integer.parseInt(prop.substring(prop.indexOf(SPACE) + 1));
			diagram.getFactors().get(index).setFactorName(value);
		}

		if(prop.startsWith(FACTOR) && prop.contains(_VALUE_)){
			int firstSpace = prop.indexOf(SPACE) + 1;
			int secondSpace = prop.indexOf(SPACE, firstSpace);
			int factorId = Integer.parseInt(prop.substring(firstSpace, secondSpace));
			int valueId = Integer.parseInt(prop.substring(prop.lastIndexOf(SPACE) + 1));
			diagram.getFactors().get(factorId).getFactorValues()[valueId] = value;
		}

		if(prop.startsWith(DECISION)){
			int index = Integer.parseInt(prop.substring(prop.indexOf(SPACE) + 1));
			diagram.getDecisions().set(index, value);
		}
	}

	@Override
	public PropertyChangeSupport getListeners() {
		return null;
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
