package com.xrosstools.xdecision.idea.editor.treeparts.definition;

import com.xrosstools.xdecision.idea.editor.XdecisionsIcons;

import javax.swing.*;

public class NamedTypeTreePart extends NamedElementTreePart {
    public NamedTypeTreePart(Object model) {
        super(model);
     }

    public String getText() {
        return getModel().toString();
    }

    public Icon getImage() {
        return XdecisionsIcons.NODE;
    }
}