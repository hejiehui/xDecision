package com.xrosstools.xdecision.idea.editor.treeparts.definition;

import com.intellij.openapi.util.IconLoader;
import com.xrosstools.idea.gef.Activator;
import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.xdecision.idea.editor.DecisionTreeEditorProvider;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElement;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class NamedElementTreePart extends AbstractTreeEditPart implements PropertyChangeListener {
    private NamedElement element;
    public NamedElementTreePart(Object model) {
        super(model);
        this.element = (NamedElement)model;
    }
    
    public String getText() {
        return element.toString();
    }

    public Icon getImage() {
        return IconLoader.findIcon(Activator.getIconPath(DecisionTreeEditorProvider.TREE));
    }

}
