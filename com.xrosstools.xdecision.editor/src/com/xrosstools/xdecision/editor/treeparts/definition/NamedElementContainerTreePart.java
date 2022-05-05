package com.xrosstools.xdecision.editor.treeparts.definition;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.xrosstools.xdecision.editor.Activator;
import com.xrosstools.xdecision.editor.model.definition.NamedElement;
import com.xrosstools.xdecision.editor.model.definition.NamedElementContainer;

public class NamedElementContainerTreePart extends NamedElementTreePart {
    private NamedElementContainer container;
    public NamedElementContainerTreePart(Object model) {
        super(model);
        container = (NamedElementContainer)model;
     }

    @Override
    protected List<NamedElement> getModelChildren() {
        return ((NamedElementContainer)getModel()).getElements();
    }
 
    protected Image getImage() {
        return Activator.getDefault().getImage(com.xrosstools.xdecision.editor.Activator.TREE);
    }
}