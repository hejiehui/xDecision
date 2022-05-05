package com.xrosstools.xdecision.idea.editor.treeparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import com.xrosstools.gef.parts.TreeEditPart;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

public class DecisionTreeDiagramTreePart extends TreeEditPart implements PropertyChangeListener {
    private DecisionTreeDiagram diagram;
    public DecisionTreeDiagramTreePart(Object model) {
        super(model);
        diagram = (DecisionTreeDiagram)model;
    }

    protected List<?> getModelChildren() {
        List children = new ArrayList();
        children.addAll(diagram.getRoots());
        children.add(diagram.getFactors());
        children.add(diagram.getDecisions());
        children.add(diagram.getUserDefinedTypes());
        children.add(diagram.getUserDefinedEnums());
        children.add(diagram.getUserDefinedConstants());

        return children;
    }

    public void activate() {
//        super.activate();
        ((DecisionTreeDiagram) getModel()).getListeners().addPropertyChangeListener(this);
    }

    public void deactivate() {
//        super.deactivate();
        ((DecisionTreeDiagram) getModel()).getListeners().removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        refresh();
    }
}
