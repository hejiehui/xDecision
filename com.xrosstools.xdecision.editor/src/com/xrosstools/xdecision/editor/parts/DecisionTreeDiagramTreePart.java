package com.xrosstools.xdecision.editor.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;

public class DecisionTreeDiagramTreePart extends AbstractTreeEditPart {
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
        children.add(diagram.getUserDefinedConstants());
        
    	return children;
    }
}
