package com.xrosstools.xdecision.editor.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.DecisionTreeRoot;

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
