package com.xross.tools.xdecision.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.xross.tools.xdecision.editor.commands.DeleteNodeCommand;
import com.xross.tools.xdecision.editor.model.DecisionTreeDiagram;
import com.xross.tools.xdecision.editor.model.DecisionTreeNode;

public class DecisionTreeNodeEditPolicy extends ComponentEditPolicy {

	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteNodeCommand(
				(DecisionTreeDiagram)(getHost().getParent().getModel()), 
				(DecisionTreeNode)(getHost().getModel()));
	}
}
