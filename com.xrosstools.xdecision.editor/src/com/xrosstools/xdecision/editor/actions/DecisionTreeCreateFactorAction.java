package com.xrosstools.xdecision.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xdecision.editor.DecisionTreeDiagramEditor;
import com.xrosstools.xdecision.editor.commands.AddFactorCommand2;
import com.xrosstools.xdecision.editor.commands.ChangeNodeFactorCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;

public class DecisionTreeCreateFactorAction extends WorkbenchPartAction implements DecisionTreeActionConstants, DecisionTreeMessages{
    private DecisionTreeNode node;
	public DecisionTreeCreateFactorAction(IWorkbenchPart part){
		super(part);
		setId(ID_PREFIX + CREATE_NEW_FACTOR);
		setText(CREATE_NEW_FACTOR_MSG);
	}
	
	public DecisionTreeCreateFactorAction(IWorkbenchPart part, DecisionTreeNode node){
	    this(part);
	    this.node = node;
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
		DecisionTreeFactor factor = new DecisionTreeFactor();
		factor.setFactorName(newValue);
		
		if(node == null)
		    execute(new AddFactorCommand2(diagram, factor));
		else {
		    CommandChain cc = new CommandChain();
		    cc.add(new AddFactorCommand2(diagram, factor));
		    cc.add(new ChangeNodeFactorCommand(node, diagram.getFactors().size()));
		    execute(cc);
		}
	}
}
