package com.xrosstools.xdecision.editor.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public class DecisionTreeConditionLocator extends ConnectionLocator {
    private int index;
    private DecisionTreeNodeConnectionPart part;

    public DecisionTreeConditionLocator(Connection c, int i, DecisionTreeNodeConnectionPart part) {
        super(c);
        this.index = i;
        this.part = part;
    }

    protected int getIndex() {
        return this.index;
    }

    protected Point getReferencePoint() {
        Connection conn = this.getConnection();
        Point p = Point.SINGLETON;
        Point p1 = conn.getPoints().getPoint(this.getIndex());
        Point p2 = conn.getPoints().getPoint(this.getIndex() + 1);
        conn.translateToAbsolute(p1);
        conn.translateToAbsolute(p2);
        
        IFigure f = ((AbstractGraphicalEditPart)part.getTarget()).getFigure();
        
        
        p.x = f.getBounds().x + f.getBounds().width/2;
        p.y = p2.y - 30;
        
        return p;
    }
}