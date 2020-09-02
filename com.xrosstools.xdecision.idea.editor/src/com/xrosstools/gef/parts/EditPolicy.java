package com.xrosstools.gef.parts;

import com.xrosstools.gef.Command;
import com.xrosstools.gef.figures.Connection;

import java.awt.*;

/**
 * If any operation is not applicable, just return null
 */
public class EditPolicy {
    private EditPart host;

    public EditPart getHost() {
        return host;
    }

    public void setHost(EditPart host) {
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
    public Command getMoveCommand(EditPart child, Rectangle constraint) {return null;}

    /**
     * Change size and/or location
     */
    public Command getChangeCommand(Rectangle constraint) {return null;}

    public boolean isSelectableSource(Object connectionModel) {return false;}

    public Command getCreateConnectionCommand(Object newConnectionModel, EditPart sourcePart) {return null;}

    public Command getReconnectSourceCommand(ConnectionEditPart connectionPart) {return null;}

    public Command getReconnectTargetCommand(ConnectionEditPart connectionPart) {return null;}
}
