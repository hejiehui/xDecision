package com.xrosstools.xdecision.editor.model;

public class DecisionTreeDecision extends NamedElement {
    public DecisionTreeDecision() {
        super(NamedElementTypeEnum.DECISION);
    }
    public DecisionTreeDecision(String name) {
        super(name, NamedElementTypeEnum.DECISION);
    }
}
