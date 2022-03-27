package com.xrosstools.xdecision.editor.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.DecisionTreeRoot;
import com.xrosstools.xdecision.editor.model.EnumType;
import com.xrosstools.xdecision.editor.model.MethodDefinition;
import com.xrosstools.xdecision.editor.model.NamedElement;
import com.xrosstools.xdecision.editor.model.NamedElementContainer;
import com.xrosstools.xdecision.editor.model.NamedType;

public class DecisionTreeTreePartFactory implements EditPartFactory {
	public EditPart createEditPart(EditPart context, Object model) {
		if(model instanceof DecisionTreeDiagram)
			return new DecisionTreeDiagramTreePart(model);

		if(model instanceof DecisionTreeRoot)
			return new DecisionTreeRootTreePart(model);

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
        
		return null;
	}
}
