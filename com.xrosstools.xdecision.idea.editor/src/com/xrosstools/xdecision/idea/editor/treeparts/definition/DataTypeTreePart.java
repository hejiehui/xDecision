package com.xrosstools.xdecision.idea.editor.treeparts.definition;

import java.util.ArrayList;
import java.util.List;

import com.xrosstools.xdecision.idea.editor.model.definition.DataType;

public class DataTypeTreePart extends NamedElementTreePart {
    private DataType type;
    public DataTypeTreePart(Object model) {
        super(model);
        type = (DataType)model;
     }

    @Override
    public List getModelChildren() {
        List a = new ArrayList();
        a.add(type.getFields());
        a.add(type.getMethods());
        return a;
    }
    
    public String getText() {
        return type.toString();
    }
}