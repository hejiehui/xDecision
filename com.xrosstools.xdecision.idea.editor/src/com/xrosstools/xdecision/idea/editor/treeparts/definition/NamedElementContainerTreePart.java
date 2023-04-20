package com.xrosstools.xdecision.idea.editor.treeparts.definition;

import com.xrosstools.xdecision.idea.editor.XdecisionsIcons;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElement;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElementContainer;

import javax.swing.*;
import java.util.List;

public class NamedElementContainerTreePart extends NamedElementTreePart {
    private NamedElementContainer container;
    public NamedElementContainerTreePart(Object model) {
        super(model);
        container = (NamedElementContainer)model;
     }

    @Override
    public List<NamedElement> getModelChildren() {
        return ((NamedElementContainer)getModel()).getElements();
    }

    public Icon getImage() {
        return XdecisionsIcons.TREE;
    }

}