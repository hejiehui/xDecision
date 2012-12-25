package com.ebay.tools.decisiontree.editor.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.ebay.tools.decisiontree.editor.model.DecisionTreeDiagram;
import com.ebay.tools.decisiontree.editor.model.DecisionTreeNode;
import com.ebay.tools.decisiontree.editor.model.DecisionTreeRoot;

public class DecisionTreeTreePartFactory implements EditPartFactory {
	public EditPart createEditPart(EditPart context, Object model) {
		if(model instanceof DecisionTreeDiagram)
			return new DecisionTreeDiagramTreePart(model);

		if(model instanceof DecisionTreeRoot)
			return new DecisionTreeRootTreePart(model);

		if(model instanceof DecisionTreeNode)
			return new DecisionTreeNodeTreePart(model);
		
		return null;
	}

}
