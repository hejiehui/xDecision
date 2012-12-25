package com.ebay.tools.decisiontree.editor.parts;

import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;

import com.ebay.tools.decisiontree.editor.model.DecisionTreeDiagram;
import com.ebay.tools.decisiontree.editor.model.DecisionTreeRoot;

public class DecisionTreeDiagramTreePart extends AbstractTreeEditPart {
    public DecisionTreeDiagramTreePart(Object model) {
        super(model);
     }

    protected List<DecisionTreeRoot> getModelChildren() {
    	return ((DecisionTreeDiagram)getModel()).getRoots();
    }
}
