package com.xross.tools.xdecision.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;

import com.ebay.tools.decisiontree.utils.DecisionTreeFactor;
import com.xross.tools.xdecision.editor.DecisionTreeDiagramEditor;
import com.xross.tools.xdecision.editor.commands.AddFactorCommand2;
import com.xross.tools.xdecision.editor.model.DecisionTreeDiagram;

public class DecisionTreeCreateFactorAction extends WorkbenchPartAction implements DecisionTreeActionConstants, DecisionTreeMessages{
	public DecisionTreeCreateFactorAction(IWorkbenchPart part){
		super(part);
		setId(ID_PREFIX + CREATE_NEW_FACTOR);
		setText(CREATE_NEW_FACTOR_MSG);
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
		execute(new AddFactorCommand2(diagram, factor));
	}
}
