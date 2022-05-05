package com.xrosstools.xdecision.idea.editor.policies;

import com.xrosstools.gef.commands.Command;
import com.xrosstools.gef.parts.EditPolicy;
import com.xrosstools.xdecision.idea.editor.commands.DeletePathCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;

public class DecisionTreeNodeConnectionEditPolicy extends EditPolicy {

    public Command getDeleteCommand() {
        return new DeletePathCommand((DecisionTreeNodeConnection)getHost().getModel());
    }
}
