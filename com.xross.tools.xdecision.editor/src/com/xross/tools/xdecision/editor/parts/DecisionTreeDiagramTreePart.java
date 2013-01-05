package com.xross.tools.xdecision.editor.parts;

import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;

import com.xross.tools.xdecision.editor.model.DecisionTreeDiagram;
import com.xross.tools.xdecision.editor.model.DecisionTreeRoot;

public class DecisionTreeDiagramTreePart extends AbstractTreeEditPart {
    public DecisionTreeDiagramTreePart(Object model) {
        super(model);
     }

    protected List<DecisionTreeRoot> getModelChildren() {
    	return ((DecisionTreeDiagram)getModel()).getRoots();
    }
}
