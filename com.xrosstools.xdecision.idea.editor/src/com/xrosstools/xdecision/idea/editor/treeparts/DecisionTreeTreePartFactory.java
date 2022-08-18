package com.xrosstools.xdecision.idea.editor.treeparts;

import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.idea.gef.parts.EditContext;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.TreeEditPart;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.definition.*;
import com.xrosstools.xdecision.idea.editor.treeparts.definition.*;

public class DecisionTreeTreePartFactory implements EditPartFactory {
	private EditContext editContext;

	public DecisionTreeTreePartFactory(EditContext editContext) {
		this.editContext = editContext;
	}

	public EditPart createEditPart(EditPart parent, Object model) {
		TreeEditPart part = null;
		if(model == null)
			return part;

		if(model instanceof DecisionTreeDiagram)
			part = new DecisionTreeDiagramTreePart(model);
		else if(model instanceof DecisionTreeNode)
			part = new DecisionTreeNodeTreePart(model);
		else if(model instanceof NamedElementContainer)
			part = new NamedElementContainerTreePart(model);
		else if(model instanceof MethodDefinition)
			part = new MethodDefinitionTreePart(model);
		else if(model instanceof NamedType)
			part = new NamedTypeTreePart(model);
		else if(model instanceof EnumType)
			part =  new EnumTypeTreePart(model);
		else if(model instanceof DataType)
			part = new DataTypeTreePart(model);
		else if(model instanceof NamedElement)
			part = new NamedElementTreePart(model);

		part.setEditPartFactory(this);
		part.setModel(model);
		part.setParent(parent);
		part.setContext(editContext);
		editContext.add(part, model);
		return part;
	}
}
