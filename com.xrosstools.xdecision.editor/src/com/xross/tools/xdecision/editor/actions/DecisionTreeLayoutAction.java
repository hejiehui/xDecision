package com.xross.tools.xdecision.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xdecision.editor.DecisionTreeDiagramEditor;
import com.xross.tools.xdecision.editor.model.DecisionTreeDiagram;
import com.xross.tools.xdecision.editor.requests.DecisionTreeLayoutRequest;

public class DecisionTreeLayoutAction extends WorkbenchPartAction implements DecisionTreeActionConstants {
	private boolean horizantal;
	private float alignment;
	
	public DecisionTreeLayoutAction(IWorkbenchPart part, String id, boolean horizantal, float alignment){
		super(part);
		setId(ID_PREFIX + id);
		this.alignment = alignment;
		this.horizantal = horizantal;
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	private Command createAlignmentCommand() {
		DecisionTreeDiagramEditor editor = (DecisionTreeDiagramEditor)getWorkbenchPart();
		DecisionTreeLayoutRequest request = new DecisionTreeLayoutRequest((DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel(), horizantal, alignment);
		return editor.getRootEditPart().getContents().getCommand(request);
	}

	public void run() {
		execute(createAlignmentCommand());
	}
}