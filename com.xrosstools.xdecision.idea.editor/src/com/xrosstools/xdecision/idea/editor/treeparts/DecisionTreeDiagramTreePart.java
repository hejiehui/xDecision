package com.xrosstools.xdecision.idea.editor.treeparts;

import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.xdecision.idea.editor.XdecisionsIcons;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class DecisionTreeDiagramTreePart extends AbstractTreeEditPart implements PropertyChangeListener {
    private DecisionTreeDiagram diagram;
    public DecisionTreeDiagramTreePart(Object model) {
        super(model);
        diagram = (DecisionTreeDiagram)model;
    }

    @Override
    public Icon getImage() {
        return XdecisionsIcons.TREE;
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
