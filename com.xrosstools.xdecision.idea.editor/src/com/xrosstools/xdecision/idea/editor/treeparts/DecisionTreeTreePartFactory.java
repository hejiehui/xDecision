package com.xrosstools.xdecision.idea.editor.treeparts;

import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.idea.gef.parts.EditContext;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.definition.*;
import com.xrosstools.xdecision.idea.editor.treeparts.definition.*;

public class DecisionTreeTreePartFactory implements EditPartFactory {
	public EditPart createEditPart(EditPart parent, Object model) {
		AbstractTreeEditPart part = null;
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

		part.setModel(model);
		return part;
	}
}
