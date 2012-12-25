package com.ebay.tools.decisiontree.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.ebay.tools.decisiontree.editor.DecisionTreeDiagramEditor;
import com.ebay.tools.decisiontree.editor.model.DecisionTreeDiagram;
import com.ebay.tools.decisiontree.editor.requests.DecisionTreeResizeRequest;

public class DecisionTreeResizeAction extends WorkbenchPartAction implements DecisionTreeActionConstants {
	private boolean horizantal;
	private boolean nodeSize;
	private boolean increase;
	
	public DecisionTreeResizeAction(IWorkbenchPart part, String id, boolean nodeSize, boolean horizantal, boolean increase){
		super(part);
		setId(ID_PREFIX + id);
		this.horizantal = horizantal;
		this.increase = increase;
		this.nodeSize = nodeSize;
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	private Command createAlignmentCommand() {
		DecisionTreeDiagramEditor editor = (DecisionTreeDiagramEditor)getWorkbenchPart();
		DecisionTreeResizeRequest request = new DecisionTreeResizeRequest((DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel(), nodeSize, horizantal, increase);
		return editor.getRootEditPart().getContents().getCommand(request);
	}

	public void run() {
		execute(createAlignmentCommand());
	}
}
