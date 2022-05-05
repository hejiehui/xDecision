package com.xrosstools.xdecision.idea.editor.model;

import com.xrosstools.xdecision.idea.editor.model.definition.NamedElement;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElementTypeEnum;

public class DecisionTreeDecision extends NamedElement {
    public DecisionTreeDecision(String name) {
        super(name, NamedElementTypeEnum.DECISION);
    }
}
