package com.xrosstools.xdecision.idea.editor.treeparts.definition;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.xrosstools.gef.parts.TreeEditPart;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElement;

public class NamedElementTreePart extends TreeEditPart implements PropertyChangeListener {
    private NamedElement element;
    public NamedElementTreePart(Object model) {
        super(model);
        this.element = (NamedElement)model;
    }
    
    protected String getText() {
        return element.toString();
    }
    
    public void activate() {
//        super.activate();
        element.getListeners().addPropertyChangeListener(this);
    }

    public void deactivate() {
//        super.deactivate();
        element.getListeners().removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        refresh();
    }
}
