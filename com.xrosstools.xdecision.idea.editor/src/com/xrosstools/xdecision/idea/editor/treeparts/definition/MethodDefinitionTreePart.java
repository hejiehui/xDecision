package com.xrosstools.xdecision.idea.editor.treeparts.definition;

import java.util.ArrayList;
import java.util.List;

import com.xrosstools.xdecision.idea.editor.model.definition.MethodDefinition;

public class MethodDefinitionTreePart extends NamedElementTreePart {
    private MethodDefinition method;
    public MethodDefinitionTreePart(Object model) {
        super(model);
        method = (MethodDefinition)model;
     }

    @Override
    public List getModelChildren() {
        List a = new ArrayList();
        a.add(method.getParameters());
        return a;
    }
}