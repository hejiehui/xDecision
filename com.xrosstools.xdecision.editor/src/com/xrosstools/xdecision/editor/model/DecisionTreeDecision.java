package com.xrosstools.xdecision.editor.model;

//TODO do we really need a dedicate decision definition?
public class DecisionTreeDecision extends NamedElement {
    public DecisionTreeDecision() {
        super(NamedElementTypeEnum.DECISION);
    }
    public DecisionTreeDecision(String name) {
        super(name, NamedElementTypeEnum.DECISION);
    }
}
