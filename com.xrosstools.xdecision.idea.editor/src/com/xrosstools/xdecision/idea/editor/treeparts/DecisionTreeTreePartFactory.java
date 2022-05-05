package com.xrosstools.xdecision.idea.editor.treeparts;

import com.xrosstools.gef.parts.TreeEditPart;
import com.xrosstools.gef.parts.TreeEditPartFactory;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.gef.parts.EditContext;
import com.xrosstools.xdecision.idea.editor.model.definition.*;
import com.xrosstools.xdecision.idea.editor.treeparts.definition.*;

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
			return new DecisionTreeDiagramTreePart(model);

		if(model instanceof DecisionTreeNode)
			return new DecisionTreeNodeTreePart(model);

		if(model instanceof NamedElementContainer)
			return new NamedElementContainerTreePart(model);

		if(model instanceof MethodDefinition)
			return new MethodDefinitionTreePart(model);

		if(model instanceof NamedType)
			return new NamedTypeTreePart(model);

		if(model instanceof EnumType)
			return new EnumTypeTreePart(model);

		if(model instanceof DataType)
			return new DataTypeTreePart(model);

		if(model instanceof NamedElement)
			return new NamedElementTreePart(model);

		part.setEditPartFactory(this);
		part.setModel(model);
		part.setParent(parent);
		part.setContext(editContext);
		editContext.add(part, model);
		return part;
	}
}
