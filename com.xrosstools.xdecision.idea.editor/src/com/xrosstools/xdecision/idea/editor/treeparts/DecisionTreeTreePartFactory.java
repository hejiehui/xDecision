package com.xrosstools.xdecision.idea.editor.treeparts;

import com.xrosstools.gef.parts.TreeEditPart;
import com.xrosstools.gef.parts.TreeEditPartFactory;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeRoot;
import com.xrosstools.gef.parts.EditContext;

public class DecisionTreeTreePartFactory implements TreeEditPartFactory {
	private EditContext editContext;

	public DecisionTreeTreePartFactory(EditContext editContext) {
		this.editContext = editContext;
	}

	public TreeEditPart createEditPart(TreeEditPart parent, Object model) {
		TreeEditPart part = null;
		if(model == null)
			return part;

		if(model instanceof DecisionTreeDiagram)
			part = new DecisionTreeDiagramTreePart();
		else if(model instanceof DecisionTreeRoot)
			part = new DecisionTreeRootTreePart();
		else if(model instanceof DecisionTreeNode)
			part = new DecisionTreeNodeTreePart();

		part.setEditPartFactory(this);
		part.setModel(model);
		part.setParent(parent);
		part.setContext(editContext);
		editContext.add(part, model);
		return part;
	}
}
