package com.xrosstools.xdecision.editor.treeparts.definition;

import java.util.List;

import com.xrosstools.xdecision.editor.model.definition.EnumType;

public class EnumTypeTreePart extends DataTypeTreePart {
    public EnumTypeTreePart(Object model) {
        super(model);
    }

    @Override
    protected List getModelChildren() {
        List children = super.getModelChildren();
        children.add(0, ((EnumType)getModel()).getValues());
        return children;
    }
}
