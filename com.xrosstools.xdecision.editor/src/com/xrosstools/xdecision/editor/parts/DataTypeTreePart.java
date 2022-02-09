package com.xrosstools.xdecision.editor.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.xrosstools.xdecision.editor.Activator;
import com.xrosstools.xdecision.editor.model.DataType;

public class DataTypeTreePart extends NamedElementTreePart {
    private DataType type;
    public DataTypeTreePart(Object model) {
        super(model);
        type = (DataType)model;
     }

    @Override
    protected List getModelChildren() {
        List a = new ArrayList();
        a.add(type.getFields());
        a.add(type.getMethods());
        return a;
    }
    
    protected String getText() {
        return type.toString();
    }
    
    protected Image getImage() {
        return Activator.getDefault().getImage(com.xrosstools.xdecision.editor.Activator.NODE);
    }
}