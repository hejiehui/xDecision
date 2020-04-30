package com.xrosstools.gef.figures;

import java.awt.*;

public abstract class AbstractAnchor {
    private Figure owner;

    public abstract Point getLocation(Point refernce);

    public Figure getOwner() {
        return owner;
    }

    public void setOwner(Figure owner) {
        this.owner = owner;
    }
}
