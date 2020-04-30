package com.xrosstools.xdecision.idea.editor.policies;

import com.xrosstools.gef.parts.EditPart;
import com.xrosstools.gef.parts.EditPolicy;
import com.xrosstools.gef.Command;
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

    public Command getOpenCommand() {
	    //TODO show dialog and get value
//        String newFactor = (String) request.getCellEditor().getValue();
//        DecisionTreeNode node = (DecisionTreeNode) getHost().getModel();
//        for(DecisionTreeFactor factor: factors){
//            if(factor.getFactorName().endsWith(newFactor))
//                return new ChangeFactorCommand(node, factors.indexOf(factor));
//        }
//
//        DecisionTreeFactor factor = new DecisionTreeFactor();
//        factor.setFactorName(newFactor);
//        return new AddFactorCommand(factors, node, factor);
        return null;
	}


    public Command getMoveCommand(EditPart child, Rectangle constraint) {return null;}

    /**
     * Change size and/or location
     */
    public Command getChangeCommand(Rectangle constraint) {
        MoveNodeCommand cmd = new MoveNodeCommand();
        cmd.setNode((DecisionTreeNode) getHost().getModel());
        cmd.setConstraint(constraint);
        return cmd;
    }

    public Command getCreateConnectionCommand(Object connectionModel, EditPart sourcePart) {
        if(sourcePart == getHost())
            return null;

        return new CreatePathCommand((DecisionTreeNodeConnection) connectionModel, (DecisionTreeNode)sourcePart.getModel(), (DecisionTreeNode)getHost().getModel());
    }

    public Command getReconnectSourceCommand(EditPart sourcePart) {
        return new ReconnectParentCommand(
                (DecisionTreeNodeConnection)sourcePart.getModel(),
                (DecisionTreeNode)getHost().getModel());
    }

    public Command getReconnectTargetCommand(EditPart targetPart) {
        return new ReconnectChildCommand(
                (DecisionTreeNodeConnection)targetPart.getModel(),
                (DecisionTreeNode)getHost().getModel());
    }
}
