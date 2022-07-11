package com.xrosstools.xdecision.idea.editor.parts;

import com.xrosstools.gef.parts.*;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.idea.editor.model.expression.*;
import com.xrosstools.xdecision.idea.editor.parts.expression.*;

public class DecisionTreePartFactory implements EditPartFactory {
	private EditContext context;
	public DecisionTreePartFactory(EditContext context) {
		this.context = context;
	}
	public EditPart createEditPart(EditPart parent, Object model) {
		GraphicalEditPart part = null;
		
		if(model == null)
			part = null;

		if(model instanceof DecisionTreeDiagram)
			part = new DecisionTreeDiagramPart();
		else if(model instanceof DecisionTreeNode)
			part = new DecisionTreeNodePart();
		else if(model instanceof DecisionTreeNodeConnection)
			part = new DecisionTreeNodeConnectionPart();
		else if(model instanceof BasicExpression)
			part = new BasicExpressionPart();
		else if(model instanceof CompositeExpression)
			part = new CompositeExpressionPart();
		else if(model instanceof EnclosedExpression)
			part = new EnclosedExpressionPart();
		else if(model instanceof MethodExpression)
			part = new MethodExpressionPart();
		else if(model instanceof VariableExpression)
			part = new VariableExpressionPart();
		else if(model instanceof ElementExpression)
			part = new ElementExpressionPart();

        part.setEditPartFactory(this);
        part.setModel(model);
        part.setParent(parent);
        part.setContext(context);
        context.add(part, model);

        return part;
	}
}
