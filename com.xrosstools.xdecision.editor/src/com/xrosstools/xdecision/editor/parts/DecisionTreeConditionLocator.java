package com.xrosstools.xdecision.editor.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.geometry.Point;

import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;

public class DecisionTreeConditionLocator extends ConnectionLocator {
    private int index;
    private DecisionTreeNodeConnection conn;

    public DecisionTreeConditionLocator(Connection c, int i, DecisionTreeNodeConnection conn) {
        super(c);
        this.index = i;
        this.conn = conn;
    }

    protected int getIndex() {
        return this.index;
    }

    protected Point getReferencePoint() {
        Point p = getConnection().getTargetAnchor().getReferencePoint().getCopy();
        
        DecisionTreeNode node = conn.getChild();
        int halfWidth = (int)(conn.getActualWidth() * 0.5);
        float alignment = node.getDecisionTreeManager().getDiagram().getAlignment();

        if(alignment == 0) {
            p.x = node.getLocation().x + halfWidth;
        } else if(alignment == 0.5) {
            p.x = node.getLocation().x + (int)(node.getActualWidth() * 0.5);
        } else {
            p.x = node.getLocation().x + node.getActualWidth() - halfWidth;
        }
        
        p.y -= 50;
        
        getConnection().translateToAbsolute(p);
        return p;
    }
}