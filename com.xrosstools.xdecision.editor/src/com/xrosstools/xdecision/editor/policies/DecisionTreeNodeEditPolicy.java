package com.xrosstools.xdecision.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.xrosstools.xdecision.editor.commands.DeleteNodeCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;

public class DecisionTreeNodeEditPolicy extends ComponentEditPolicy {

	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteNodeCommand(
				(DecisionTreeDiagram)(getHost().getParent().getModel()), 
				(DecisionTreeNode)(getHost().getModel()));
	}
}
