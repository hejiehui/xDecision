package com.xrosstools.xdecision.idea.editor.model;

import com.xrosstools.xdecision.idea.editor.model.definition.NamedElementTypeEnum;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedType;

public class DecisionTreeFactor extends NamedType {
	public DecisionTreeFactor(DecisionTreeDiagram diagram, String name) {
		super(diagram, name, NamedElementTypeEnum.FACTOR, DEFAULT_TYPE);
	}

	public String getFactorName() {
		return getName();
	}
	public void setFactorName(String factorName) {
		setName(factorName);
	}
}
