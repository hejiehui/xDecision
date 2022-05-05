package com.xrosstools.xdecision.idea.editor.model;

import com.xrosstools.xdecision.idea.editor.model.definition.NamedElementContainer;
import com.xrosstools.xdecision.idea.editor.model.expression.ExpressionParser;

import java.util.List;

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
}
