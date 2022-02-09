package com.xrosstools.xdecision.editor.parts;

import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import com.xrosstools.xdecision.editor.Activator;
import com.xrosstools.xdecision.editor.model.IdentifierContainer;

public class IdentifierContainerTreePart extends AbstractTreeEditPart {
    public IdentifierContainerTreePart(Object model) {
        super(model);
     }

    @Override
    protected List getModelChildren() {
        return ((IdentifierContainer)getModel()).getElements();
    }
    
    protected String getText() {
        return getModel().toString();
    }
    
    protected Image getImage() {
        return Activator.getDefault().getImage(com.xrosstools.xdecision.editor.Activator.TREE);
    }
}