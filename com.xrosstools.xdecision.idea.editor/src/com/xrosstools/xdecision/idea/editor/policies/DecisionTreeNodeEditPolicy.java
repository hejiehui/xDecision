package com.xrosstools.xdecision.idea.editor.policies;

import com.xrosstools.idea.gef.parts.AbstractConnectionEditPart;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xdecision.idea.editor.commands.*;
import com.xrosstools.xdecision.idea.editor.model.*;

import java.awt.*;

public class DecisionTreeNodeEditPolicy extends EditPolicy {
	public Command getDeleteCommand() {
		return new DeleteNodeCommand(
				(DecisionTreeDiagram)(getHost().getParent().getModel()),
				(DecisionTreeNode)(getHost().getModel()));
	}

    public boolean isSelectableSource(Object connectionModel) {return true;}

    public Command getMoveCommand(AbstractGraphicalEditPart child, Rectangle constraint) {return null;}

    /**
     * Change size and/or location
     */
    public Command getChangeCommand(Rectangle constraint) {
        MoveNodeCommand cmd = new MoveNodeCommand();
        cmd.setNode((DecisionTreeNode) getHost().getModel());
        cmd.setConstraint(constraint);
        return cmd;
    }

    public Command getCreateConnectionCommand(Object connectionModel, AbstractGraphicalEditPart sourcePart) {
        if(sourcePart == getHost())
            return null;

        return new CreatePathCommand((DecisionTreeNodeConnection) connectionModel, (DecisionTreeNode)sourcePart.getModel(), (DecisionTreeNode)getHost().getModel());
    }

    public Command getReconnectSourceCommand(AbstractConnectionEditPart connectionPart) {
        return new ReconnectParentCommand(
                (DecisionTreeNodeConnection)connectionPart.getModel(),
                (DecisionTreeNode)getHost().getModel());
    }

    public Command getReconnectTargetCommand(AbstractConnectionEditPart connectionPart) {
        return new ReconnectChildCommand(
                (DecisionTreeNodeConnection)connectionPart.getModel(),
                (DecisionTreeNode)getHost().getModel());
    }
}
