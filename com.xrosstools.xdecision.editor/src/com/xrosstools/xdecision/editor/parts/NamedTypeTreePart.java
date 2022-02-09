package com.xrosstools.xdecision.editor.parts;

import org.eclipse.swt.graphics.Image;

import com.xrosstools.xdecision.editor.Activator;

public class NamedTypeTreePart extends NamedElementTreePart {
    public NamedTypeTreePart(Object model) {
        super(model);
     }

    protected String getText() {
        return getModel().toString();
    }
    
    protected Image getImage() {
        return Activator.getDefault().getImage(com.xrosstools.xdecision.editor.Activator.NODE);
    }
}