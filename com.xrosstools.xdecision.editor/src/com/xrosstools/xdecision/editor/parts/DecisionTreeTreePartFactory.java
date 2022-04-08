package com.xrosstools.xdecision.editor.parts;

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
import com.xrosstools.xdecision.editor.parts.definition.DataTypeTreePart;
import com.xrosstools.xdecision.editor.parts.definition.EnumTypeTreePart;
import com.xrosstools.xdecision.editor.parts.definition.MethodDefinitionTreePart;
import com.xrosstools.xdecision.editor.parts.definition.NamedElementContainerTreePart;
import com.xrosstools.xdecision.editor.parts.definition.NamedElementTreePart;
import com.xrosstools.xdecision.editor.parts.definition.NamedTypeTreePart;

public class DecisionTreeTreePartFactory implements EditPartFactory {
	public EditPart createEditPart(EditPart context, Object model) {
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
        
		return null;
	}
}
