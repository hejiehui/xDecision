package com.xrosstools.xdecision.editor.treeparts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.definition.DataType;
import com.xrosstools.xdecision.editor.model.definition.EnumType;
import com.xrosstools.xdecision.editor.model.definition.MethodDefinition;
import com.xrosstools.xdecision.editor.model.definition.NamedElement;
import com.xrosstools.xdecision.editor.model.definition.NamedElementContainer;
import com.xrosstools.xdecision.editor.model.definition.NamedType;
import com.xrosstools.xdecision.editor.treeparts.definition.DataTypeTreePart;
import com.xrosstools.xdecision.editor.treeparts.definition.EnumTypeTreePart;
import com.xrosstools.xdecision.editor.treeparts.definition.MethodDefinitionTreePart;
import com.xrosstools.xdecision.editor.treeparts.definition.NamedElementContainerTreePart;
import com.xrosstools.xdecision.editor.treeparts.definition.NamedElementTreePart;
import com.xrosstools.xdecision.editor.treeparts.definition.NamedTypeTreePart;

public class DecisionTreeTreePartFactory implements EditPartFactory {
	public EditPart createEditPart(EditPart context, Object model) {
	    EditPart part = null;

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
        
		return part;
	}
}
