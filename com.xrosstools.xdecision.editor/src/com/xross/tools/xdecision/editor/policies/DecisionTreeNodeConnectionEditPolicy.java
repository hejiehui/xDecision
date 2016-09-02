package com.xross.tools.xdecision.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.xross.tools.xdecision.editor.commands.DeletePathCommand;
import com.xross.tools.xdecision.editor.model.DecisionTreeNodeConnection;

public class DecisionTreeNodeConnectionEditPolicy extends ComponentEditPolicy{

    protected Command createDeleteCommand(GroupRequest deleteRequest) {
        return new DeletePathCommand((DecisionTreeNodeConnection)getHost().getModel());
    }
}
