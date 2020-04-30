package com.xrosstools.gef.figures;

import java.awt.*;

public class Endpoint extends Figure {
    private final int SIZE = 5;

    public Connection getParentConnection() {
        return (Connection)getParent();
    }

    public boolean isConnectionEndpoint() {
        return getParentConnection() instanceof  Connection;
    }

    public boolean isConnectionSourceEndpoint() {
        return isConnectionEndpoint() && getParentConnection().getSourceEndpoint() == this;
    }

    public boolean isConnectionTargetEndpoint() {
        return isConnectionEndpoint() && getParentConnection().getTargetEndpoint() == this;
    }


    @Override
    public void paint(Graphics graphics) {
        if(getParentConnection().isSelected())
            graphics.fill3DRect(getX(), getY(), SIZE, SIZE, true);
    }

    @Override
    public final int getWidth() {
        return SIZE;
    }

    @Override
    public final int getHeight() {
        return SIZE;
    }
}
