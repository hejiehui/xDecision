package com.xrosstools.xdecision.idea.editor.treeparts.definition;

import com.intellij.openapi.util.IconLoader;
import com.xrosstools.idea.gef.Activator;
import com.xrosstools.xdecision.idea.editor.DecisionTreeEditorProvider;

import javax.swing.*;

public class NamedTypeTreePart extends NamedElementTreePart {
    public NamedTypeTreePart(Object model) {
        super(model);
     }

    public String getText() {
        return getModel().toString();
    }

    public Icon getImage() {
        return IconLoader.findIcon(Activator.getIconPath(DecisionTreeEditorProvider.NODE));
    }
}