package com.xrosstools.xdecision.idea.editor.parts;

import com.xrosstools.gef.parts.EditContext;
import com.xrosstools.gef.parts.EditPart;
import com.xrosstools.gef.parts.EditPartFactory;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;

public class DecisionTreePartFactory implements EditPartFactory {
	private EditContext context;
	public DecisionTreePartFactory(EditContext context) {
		this.context = context;
	}
	public EditPart createEditPart(EditPart parent, Object model) {
		EditPart part = null;
		
		if(model == null)
			part = null;
		
		if(model instanceof DecisionTreeDiagram)
			part = new DecisionTreeDiagramPart();
		else
		if(model instanceof DecisionTreeNode)
			part = new DecisionTreeNodePart();
		else
		if(model instanceof DecisionTreeNodeConnection)
			part = new DecisionTreeNodeConnectionPart();


        part.setEditPartFactory(this);
        part.setModel(model);
        part.setParent(parent);
        part.setContext(context);
        context.add(part, model);

        return part;
	}
}
