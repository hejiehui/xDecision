package com.xrosstools.xdecision.idea.editor.parts;

import com.xrosstools.gef.figures.Connection;
import com.xrosstools.gef.routers.ConnectionLocator;
import com.xrosstools.gef.routers.PointList;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;
import java.awt.*;

public class DecisionTreeConditionLocator implements ConnectionLocator {
    private int index;
    private DecisionTreeNodeConnection conn;
    private static final int NODE_LABEL_GAP = 20;

    public DecisionTreeConditionLocator(DecisionTreeNodeConnection conn) {
        this.conn = conn;
    }

    protected int getIndex() {
        return this.index;
    }

    public Point getLocation(PointList points) {
        DecisionTreeNode node = conn.getChild();

        int halfWidth = (int)(conn.getActualWidth() * 0.5);

        return node.getDecisionTreeManager().getDiagram().isHorizantal() ?
            getHorizantalLayoutPoint(node, halfWidth) :getVerticalLayoutPoint(node, halfWidth);
    }
    
    private Point getVerticalLayoutPoint(DecisionTreeNode node, int halfWidth) {
        Point p = new Point(node.getLocation());
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
        
//        getConnection().translateToAbsolute(p);
        return p;
    }
    
    private Point getHorizantalLayoutPoint(DecisionTreeNode node, int halfWidth) {
        Point p = new Point(node.getLocation());
        p.x += halfWidth;
        p.y -= NODE_LABEL_GAP;
        
//        getConnection().translateToAbsolute(p);
        return p;
    }
}