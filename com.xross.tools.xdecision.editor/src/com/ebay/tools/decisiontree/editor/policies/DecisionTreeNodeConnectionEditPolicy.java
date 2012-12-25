package com.ebay.tools.decisiontree.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.ebay.tools.decisiontree.editor.commands.DeletePathCommand;
import com.ebay.tools.decisiontree.editor.model.DecisionTreeNodeConnection;

public class DecisionTreeNodeConnectionEditPolicy extends ComponentEditPolicy{

    protected Command createDeleteCommand(GroupRequest deleteRequest) {
        return new DeletePathCommand((DecisionTreeNodeConnection)getHost().getModel());
    }
}
