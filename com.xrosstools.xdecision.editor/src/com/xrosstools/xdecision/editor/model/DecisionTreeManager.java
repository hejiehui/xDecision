package com.xrosstools.xdecision.editor.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.xrosstools.xdecision.editor.model.definition.NamedElementContainer;
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
}
