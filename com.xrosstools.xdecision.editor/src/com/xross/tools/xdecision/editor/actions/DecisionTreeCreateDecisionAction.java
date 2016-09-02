package com.xross.tools.xdecision.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xdecision.editor.DecisionTreeDiagramEditor;
import com.xross.tools.xdecision.editor.commands.AddDecisionCommand;
import com.xross.tools.xdecision.editor.model.DecisionTreeDiagram;

public class DecisionTreeCreateDecisionAction extends WorkbenchPartAction implements DecisionTreeActionConstants, DecisionTreeMessages{
	public DecisionTreeCreateDecisionAction(IWorkbenchPart part){
		super(part);
		setId(ID_PREFIX + CREATE_NEW_DECISION);
		setText(CREATE_NEW_DECISION_MSG);
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), "Create new decision: ", "Decision", "new decision", null);
		if (dlg.open() != Window.OK)
			return;
		String newValue = dlg.getValue();
		
		DecisionTreeDiagramEditor editor = (DecisionTreeDiagramEditor)getWorkbenchPart();
		DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
		execute(new AddDecisionCommand(diagram, newValue));
	}
}
