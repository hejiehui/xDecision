package com.xrosstools.xdecision.idea.editor.treeparts.definition;

import com.xrosstools.xdecision.idea.editor.XdecisionsIcons;
import com.xrosstools.xdecision.idea.editor.model.definition.DataType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

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

    public Icon getImage() {
        return XdecisionsIcons.NODE;
    }
}