package com.xrosstools.xdecision.editor.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.editor.model.OperatorReference;
import com.xrosstools.xdecision.editor.model.expression.BasicExpression;
import com.xrosstools.xdecision.editor.model.expression.CompositeExpression;
import com.xrosstools.xdecision.editor.model.expression.ElementExpression;
import com.xrosstools.xdecision.editor.model.expression.EnclosedExpression;
import com.xrosstools.xdecision.editor.model.expression.ExtensibleExpression;
import com.xrosstools.xdecision.editor.model.expression.MethodExpression;
import com.xrosstools.xdecision.editor.model.expression.VariableExpression;
import com.xrosstools.xdecision.editor.parts.expression.BasicExpressionPart;
import com.xrosstools.xdecision.editor.parts.expression.CompositeExpressionPart;
import com.xrosstools.xdecision.editor.parts.expression.ElementExpressionPart;
import com.xrosstools.xdecision.editor.parts.expression.EnclosedExpressionPart;
import com.xrosstools.xdecision.editor.parts.expression.ExtensibleExpressionPart;
import com.xrosstools.xdecision.editor.parts.expression.MethodExpressionPart;
import com.xrosstools.xdecision.editor.parts.expression.VariableExpressionPart;

public class DecisionTreePartFactory implements EditPartFactory {
	public EditPart createEditPart(EditPart context, Object model) {
		if(model == null)
			return null;
		
        EditPart part = null;
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
        else if(model instanceof OperatorReference)
            part = new OperatorReferencePart();
            
		part.setModel(model);
		
		return part;
	}
}
