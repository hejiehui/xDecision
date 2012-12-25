package com.ebay.tools.decisiontree.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.ebay.tools.decisiontree.editor.commands.DeleteNodeCommand;
import com.ebay.tools.decisiontree.editor.model.DecisionTreeDiagram;
import com.ebay.tools.decisiontree.editor.model.DecisionTreeNode;

public class DecisionTreeNodeEditPolicy extends ComponentEditPolicy {

	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteNodeCommand(
				(DecisionTreeDiagram)(getHost().getParent().getModel()), 
				(DecisionTreeNode)(getHost().getModel()));
	}
}
