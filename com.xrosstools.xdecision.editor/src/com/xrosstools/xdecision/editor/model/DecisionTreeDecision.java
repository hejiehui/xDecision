package com.xrosstools.xdecision.editor.model;

import com.xrosstools.xdecision.editor.model.definition.NamedElement;
import com.xrosstools.xdecision.editor.model.definition.NamedElementTypeEnum;

public class DecisionTreeDecision extends NamedElement {
    public DecisionTreeDecision(String name) {
        super(name, NamedElementTypeEnum.DECISION);
    }
}
