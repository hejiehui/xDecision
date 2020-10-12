package com.xrosstools.xdecision.editor.model;

import java.util.List;

public class DecisionTreeManager {
	private DecisionTreeDiagram diagram;
	public DecisionTreeManager(DecisionTreeDiagram diagram){
		this.diagram = diagram;
	}
	
	public String[] getDecisions(){
		return diagram.getDecisions().toArray(new String[diagram.getDecisions().size()]);
	}
	
	public String getDecision(int decisionId){
		return diagram.getDecisions().get(decisionId);
	}
	
	public void changeDecision(int decisionId, String newValue){
		diagram.getDecisions().set(decisionId, newValue);
	}
	
	/**
	 * if decision is already exist, return the id,
	 * if not, create a new decision and return the new id
	 */
	public int getDecisionId(String decision){
		List<String> decisions = diagram.getDecisions();
		for(int i = 0; i < decisions.size(); i++)
			if(decisions.get(i).equals(decision))
				return i;
		
		decisions.add(decision);
		return decisions.size()-1;
	}

	public String[] getFactorNames(){
		String[] names = new String[diagram.getFactors().size()];
		for(int i = 0; i<names.length;i++)
			names[i] = diagram.getFactors().get(i).getFactorName();
		return names;
	}
	
	public String getFactorName(int factorId){
		return diagram.getFactors().get(factorId).getFactorName();
	}
	
	public void changeFactorName(int factorId, String newName){
		diagram.getFactors().get(factorId).setFactorName(newName);
	}
	
	/**
	 * if name is already exist, returen the id,
	 * if not, create a new factor and return the new id
	 * @param factorName
	 * @return
	 */
	public int getFactorId(String factorName){
		List<DecisionTreeFactor> factors = diagram.getFactors();
		for(int i = 0; i < factors.size(); i++)
			if(factors.get(i).getFactorName().equals(factorName))
				return i;
		
		DecisionTreeFactor factor = new DecisionTreeFactor();
		factor.setFactorName(factorName);
		factors.add(factor);
		return factors.size()-1;
	}
	
	public String[] getFactorValues(int factorId){
	    if(factorId == -1)
	        return new String[0];
		return diagram.getFactors().get(factorId).getFactorValues();
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
		String[] values = diagram.getFactors().get(factorId).getFactorValues();
		for(int i = 0; i < values.length; i++)
			if(values[i].equals(factorName))
				return i;
		
		String[] newValues = new String[values.length + 1];
		
		for(int i = 0; i < values.length; i++)
			newValues[i] = values[i];

		newValues[values.length] = factorName;
		diagram.getFactors().get(factorId).setFactorValues(newValues);
		return newValues.length-1;
	}
}
