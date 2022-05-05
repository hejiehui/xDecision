package com.xrosstools.xdecision.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xdecision.editor.DecisionTreeDiagramEditor;
import com.xrosstools.xdecision.editor.commands.AddFactorCommand2;
import com.xrosstools.xdecision.editor.commands.expression.ChangeChildCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.expression.VariableExpression;

public class DecisionTreeCreateFactorAction extends BaseDialogAction implements DecisionTreeActionConstants, DecisionTreeMessages{
    private DecisionTreeNode node;
    private String typeName;
	
	public DecisionTreeCreateFactorAction(IWorkbenchPart part, DecisionTreeNode node, String typeName){
	    super(part, CREATE_NEW_FACTOR, "Factor", "new factor");
	    this.node = node;
	    this.typeName = typeName;
	    setText(typeName);
	}
	
    @Override
    protected Command createCommand(String value) {
		DecisionTreeDiagramEditor editor = (DecisionTreeDiagramEditor)getWorkbenchPart();
		DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
		DecisionTreeFactor factor = new DecisionTreeFactor(diagram, value);
		factor.setType(diagram.findDataType(typeName));
		
	    CommandChain cc = new CommandChain();
	    cc.add(new AddFactorCommand2(diagram, factor));
	    cc.add(new ChangeChildCommand(node, node.getNodeExpression(), new VariableExpression(factor.getFactorName())));
	    return cc;
	}
}
