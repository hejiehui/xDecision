package com.xrosstools.xdecision.editor.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.ExpressionParser;

public class DecisionTreeManager {
	private DecisionTreeDiagram diagram;
	private ExpressionParser parser;
	public DecisionTreeManager(DecisionTreeDiagram diagram){
		this.diagram = diagram;
		parser = new ExpressionParser(this);
	}
	
	public DecisionTreeDiagram getDiagram() {
        return diagram;
    }

    public ExpressionParser getParser() {
        return parser;
    }

    public NamedElementContainer<DecisionTreeDecision> getDecisions(){
		return diagram.getDecisions();
	}

    public NamedElementContainer<DecisionTreeFactor> getFactors(){
        return diagram.getFactors();
    }

	public String[] getFactorNames(){
		String[] names = new String[diagram.getFactorList().size()];
		for(int i = 0; i<names.length;i++)
			names[i] = getFactor(i).getFactorName();
		return names;
	}
	
	public String getFactorName(int factorId){
		return getFactor(factorId).getFactorName();
	}

	public DecisionTreeFactor getFactor(int factorId) {
        return diagram.getFactorList().get(factorId);
    }
	
	public void changeFactorName(int factorId, String newName){
		getFactor(factorId).setFactorName(newName);
	}

	public String[] getFactorValues(int factorId){
	    if(factorId == -1)
	        return new String[0];
		return getFactor(factorId).getFactorValues();
	}
	
    public Map<String, Set<String>> getExpressionValues(ExpressionDefinition exp){
        Map<String, Set<String>> choices = new LinkedHashMap<String, Set<String>>();
        if(exp == null)
            return choices;
        
//        for(DecisionTreeNode node: diagram.getNodes()) {
//            ExpressionDefinition nodeExp = node.getNodeExpression();
//            if(nodeExp == null || !nodeExp.toString().equals(exp.toString()))
//                continue;
//            
//            for(DecisionTreeNodeConnection conn: node.getOutputs()) {
//                choices.put(conn.get, value)
//            }
//                
//        }
//        
//        
//        return getFactor(factorId).getFactorValues();
        return null;
    }
    
	public String getFactorValue(int factorId, int valueId){
		return getFactorValues(factorId)[valueId];
	}
	
	public void changeFactorValue(int factorId, int factorValueId, String newValue){
		getFactorValues(factorId)[factorValueId] = newValue;
	}
	 
	/**
	 * if value is already exist, return the id,
	 * if not, create a new factor value and return the new id
	 * @param factorName
	 * @return
	 */
	public int getFactorValueId(int factorId, String factorName){
		String[] values = getFactor(factorId).getFactorValues();
		for(int i = 0; i < values.length; i++)
			if(values[i].equals(factorName))
				return i;
		
		String[] newValues = new String[values.length + 1];
		
		for(int i = 0; i < values.length; i++)
			newValues[i] = values[i];

		newValues[values.length] = factorName;
		getFactor(factorId).setFactorValues(newValues);
		return newValues.length-1;
	}
}
