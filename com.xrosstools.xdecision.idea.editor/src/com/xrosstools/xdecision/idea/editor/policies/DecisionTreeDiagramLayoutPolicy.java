package com.xrosstools.xdecision.idea.editor.policies;

import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xdecision.idea.editor.commands.CreateNodeCommand;
import com.xrosstools.xdecision.idea.editor.commands.MoveNodeCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;

import java.awt.*;

public class DecisionTreeDiagramLayoutPolicy extends EditPolicy {
    public Command getCreateCommand(Object newModel, Point location) {
        return new CreateNodeCommand(
                (DecisionTreeDiagram)getHost().getModel(),
                (DecisionTreeNode)newModel,
                location);
    }

    public Command getMoveCommand(AbstractGraphicalEditPart child, Rectangle constraint) {
        MoveNodeCommand cmd = new MoveNodeCommand();
        if(!(child.getModel() instanceof DecisionTreeNode))
            return null;

        cmd.setNode((DecisionTreeNode) child.getModel());
        cmd.setConstraint(constraint);
        return cmd;
    }
}