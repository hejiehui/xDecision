package com.xrosstools.xdecision.idea.editor.treeparts;

import com.xrosstools.gef.parts.TreeEditPart;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class DecisionTreeDiagramTreePart extends TreeEditPart implements PropertyChangeListener {
    private DecisionTreeDiagram diagram;
    public DecisionTreeDiagramTreePart(Object model) {
        super(model);
        diagram = (DecisionTreeDiagram)model;
    }

    public List getModelChildren() {
        List children = new ArrayList();
        children.addAll(diagram.getRoots());
        children.add(diagram.getFactors());
        children.add(diagram.getDecisions());
        children.add(diagram.getUserDefinedTypes());
        children.add(diagram.getUserDefinedEnums());
        children.add(diagram.getUserDefinedConstants());

        return children;
    }
}
