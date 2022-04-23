package com.xrosstools.xdecision.editor.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;

public class DecisionTreeConditionLocator extends ConnectionLocator {
    private int index;
    private DecisionTreeNodeConnection conn;
    private static final int NODE_LABEL_GAP = 20;

    public DecisionTreeConditionLocator(Connection c, int i, DecisionTreeNodeConnection conn) {
        super(c);
        this.index = i;
        this.conn = conn;
    }

    protected int getIndex() {
        return this.index;
    }

    protected Point getReferencePoint() {
        DecisionTreeNode node = conn.getChild();

        int halfWidth = (int)(conn.getActualWidth() * 0.5);

        return node.getDecisionTreeManager().getDiagram().isHorizantal() ?
            getHorizantalLayoutPoint(node, halfWidth) :getVerticalLayoutPoint(node, halfWidth);
    }
    
    private Point getVerticalLayoutPoint(DecisionTreeNode node, int halfWidth) {
        Point p = node.getLocation().getCopy();
        float alignment = node.getDecisionTreeManager().getDiagram().getAlignment();
        Dimension size = node.getSize();

        if(alignment == 0) {
            p.x += halfWidth;
        } else if(alignment == 0.5) {
            p.x += (int)(size.width * 0.5);
        } else {
            p.x += size.width - halfWidth;
        }
        
        p.y -= NODE_LABEL_GAP;
        
        getConnection().translateToAbsolute(p);
        return p;
    }
    
    private Point getHorizantalLayoutPoint(DecisionTreeNode node, int halfWidth) {
        Point p = node.getLocation().getCopy();
        p.x += halfWidth;
        p.y -= NODE_LABEL_GAP;
        
        getConnection().translateToAbsolute(p);
        return p;
    }
}