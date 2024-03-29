package com.xrosstools.xdecision.idea.editor.treeparts.definition;

import java.util.List;

import com.xrosstools.xdecision.idea.editor.model.definition.EnumType;

public class EnumTypeTreePart extends DataTypeTreePart {
    public EnumTypeTreePart(Object model) {
        super(model);
    }

    @Override
    public List getModelChildren() {
        List children = super.getModelChildren();
        children.add(0, ((EnumType)getModel()).getValues());
        return children;
    }
}
