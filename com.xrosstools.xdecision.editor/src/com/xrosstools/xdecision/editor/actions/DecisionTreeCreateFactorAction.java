package com.xrosstools.xdecision.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xdecision.editor.DecisionTreeDiagramEditor;
import com.xrosstools.xdecision.editor.commands.AddFactorCommand2;
import com.xrosstools.xdecision.editor.commands.expression.ChangeChildCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.expression.VariableExpression;

public class DecisionTreeCreateFactorAction extends WorkbenchPartAction implements DecisionTreeActionConstants, DecisionTreeMessages{
    private DecisionTreeNode node;
    private String typeName;
	public DecisionTreeCreateFactorAction(IWorkbenchPart part){
		super(part);
		setId(ID_PREFIX + CREATE_NEW_FACTOR);
	}
	
	public DecisionTreeCreateFactorAction(IWorkbenchPart part, DecisionTreeNode node, String typeName){
	    this(part);
	    this.node = node;
	    this.typeName = typeName;
	    setText(typeName);
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), "Create new factor: ", "Factor", "new factor", null);
		if (dlg.open() != Window.OK)
			return;
		String newValue = dlg.getValue();

		DecisionTreeDiagramEditor editor = (DecisionTreeDiagramEditor)getWorkbenchPart();
		DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
		DecisionTreeFactor factor = new DecisionTreeFactor(diagram, newValue);
		factor.setType(diagram.findDataType(typeName));
		
		if(node == null)
		    execute(new AddFactorCommand2(diagram, factor));
		else {
		    CommandChain cc = new CommandChain();
		    cc.add(new AddFactorCommand2(diagram, factor));
		    cc.add(new ChangeChildCommand(node, node.getNodeExpression(), new VariableExpression(factor.getFactorName())));
		    execute(cc);
		}
	}
}
