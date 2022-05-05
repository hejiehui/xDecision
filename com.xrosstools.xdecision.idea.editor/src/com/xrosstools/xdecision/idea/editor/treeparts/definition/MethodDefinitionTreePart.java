package com.xrosstools.xdecision.idea.editor.treeparts.definition;

import java.util.ArrayList;
import java.util.List;

import com.xrosstools.xdecision.editor.model.definition.MethodDefinition;

public class MethodDefinitionTreePart extends NamedElementTreePart {
    private MethodDefinition method;
    public MethodDefinitionTreePart(Object model) {
        super(model);
        method = (MethodDefinition)model;
     }

    @Override
    protected List getModelChildren() {
        List a = new ArrayList();
        a.add(method.getParameters());
        return a;
    }
}