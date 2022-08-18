package com.xrosstools.idea.gef.parts;

import com.xrosstools.idea.gef.commands.Command;

import java.awt.*;

/**
 * If any operation is not applicable, just return null
 */
public class EditPolicy {
    private GraphicalEditPart host;

    public GraphicalEditPart getHost() {
        return host;
    }

    public void setHost(GraphicalEditPart host) {
        this.host = host;
    }

    /**
     * Delete current element
     */
    public Command getDeleteCommand() {return null;}

    /**
     * Add a newly created element
     */
    public Command getCreateCommand(Object newModel, Point location) {return null;}

    /**
     * Move an element from elsewhere
     */
    public Command getMoveCommand(GraphicalEditPart child, Rectangle constraint) {return null;}

    /**
     * Change size and/or location
     */
    public Command getChangeCommand(Rectangle constraint) {return null;}

    public boolean isSelectableSource(Object connectionModel) {return false;}

    public Command getCreateConnectionCommand(Object newConnectionModel, GraphicalEditPart sourcePart) {return null;}

    public Command getReconnectSourceCommand(ConnectionEditPart connectionPart) {return null;}

    public Command getReconnectTargetCommand(ConnectionEditPart connectionPart) {return null;}
}
