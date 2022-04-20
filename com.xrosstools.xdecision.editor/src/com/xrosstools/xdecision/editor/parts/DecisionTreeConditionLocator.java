package com.xrosstools.xdecision.editor.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.geometry.Point;

import com.xrosstools.xdecision.editor.model.DecisionTreeNode;

public class DecisionTreeConditionLocator extends ConnectionLocator {
    private int index;

    public DecisionTreeConditionLocator(Connection c, int i) {
        super(c);
        this.index = i;
    }

    protected int getIndex() {
        return this.index;
    }

    protected Point getReferencePoint() {
        Connection conn = this.getConnection();
        Point p = conn.getTargetAnchor().getReferencePoint().getCopy();
        p.y -= 50;
        return p;
    }
}