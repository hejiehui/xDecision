package com.ebay.tools.decisiontree.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import com.ebay.tools.decisiontree.editor.commands.CreatePathCommand;
import com.ebay.tools.decisiontree.editor.commands.ReconnectChildCommand;
import com.ebay.tools.decisiontree.editor.commands.ReconnectParentCommand;
import com.ebay.tools.decisiontree.editor.model.DecisionTreeNode;
import com.ebay.tools.decisiontree.editor.model.DecisionTreeNodeConnection;

public class DecisionTreeGraphicNodeEditPolicy extends GraphicalNodeEditPolicy {
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		CreatePathCommand cmd = (CreatePathCommand)request.getStartCommand();
		cmd.setChild((DecisionTreeNode)getHost().getModel());
		return cmd;
	}

	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		CreatePathCommand cmd = new CreatePathCommand();
		cmd.setParent((DecisionTreeNode)getHost().getModel());
		request.setStartCommand(cmd);
		return cmd;
	}

	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		return new ReconnectParentCommand(
				(DecisionTreeNodeConnection)request.getConnectionEditPart().getModel(), 
				(DecisionTreeNode)getHost().getModel());
	}

	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		return new ReconnectChildCommand(
				(DecisionTreeNodeConnection)request.getConnectionEditPart().getModel(), 
				(DecisionTreeNode)getHost().getModel());
	}

}
