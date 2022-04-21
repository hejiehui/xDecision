package com.xrosstools.xdecision.editor.layout;

import org.eclipse.draw2d.geometry.Point;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;

public class LayoutAlgorithm {
    private int charWidth = 10;
    private int margin = 100;
    private int minWidth = 100;
    private int horizantalSpace;
    private int verticalSpace;
    private int nodeHeight;
    private float alignment;

    boolean useNew = true;

    public void layout(DecisionTreeDiagram diagram) {
        horizantalSpace = diagram.isHorizantal() ? diagram.getVerticalSpace() : diagram.getHorizantalSpace();
        verticalSpace = diagram.isHorizantal() ? diagram.getHorizantalSpace() : diagram.getVerticalSpace();

        nodeHeight = diagram.isHorizantal() ? diagram.getNodeWidth() : diagram.getNodeHeight();

        alignment = diagram.getAlignment();

        int branchWidth = 0;
        int nextLeftPos = margin;
        for (DecisionTreeNode node : diagram.getRoots()) {
            branchWidth += layout(nextLeftPos + branchWidth, node, 0) + horizantalSpace;
        }
    }

    private int layout(int leftPos, DecisionTreeNode node, int depth) {
        int conditionWidth = getConnWidth(node.getInput());
        int nodeWidth = getNodeWidth(node);
        int condiNodeWidth = Math.max(conditionWidth, nodeWidth);

        int branchWidth = 0;
        int nextLeftPos = leftPos;
        for (DecisionTreeNodeConnection path : node.getOutputs()) {
            branchWidth += layout(nextLeftPos + branchWidth, path.getChild(), depth + 1) + horizantalSpace;
        }
        branchWidth = branchWidth == 0 ? 0 : branchWidth - horizantalSpace;
        
        int x = leftPos + locateLower(condiNodeWidth, nodeWidth);
        int y = margin + (depth) * (verticalSpace + nodeHeight);
        
        /**
         *   conditionconditionconditioncondition
         *              branchbranch
         */
        if(condiNodeWidth >= branchWidth) {
            relocateBranch(node, locateLower(condiNodeWidth, branchWidth));
        } else {
            x += locateLower(branchWidth, condiNodeWidth);
        }
            
        node.setLocation(new Point(x, y));
        //Make sure connection get refreshed
        if(node.getInput() != null)
            node.getInput().layout();

        return Math.max(branchWidth, condiNodeWidth);
    }

    private int getNodeWidth(DecisionTreeNode node) {
        if (node.getActualWidth() > 0)
            return node.getActualWidth();

        int width = node.getNodeExpression() == null ? 0 : node.getNodeExpression().toString().length();

        width *= charWidth;// average char width
        width += 10;// gap

        return Math.max(width, minWidth);
    }

    private int getConnWidth(DecisionTreeNodeConnection conn) {
        if (conn == null)
            return 0;

        if (conn.getActualWidth() > 0)
            return conn.getActualWidth();

        int conditionCharCount = 0;

        conditionCharCount = conn.getOperator() == null ? 0 : conn.getOperator().toString().length();
        conditionCharCount += conn.getExpression() == null ? 0 : conn.getExpression().toString().length();

        return conditionCharCount * charWidth / 2;
    }
    
    /**
     *   upperWidthupperWidthupperWidth
     *             lowerWidth
     */
    private int locateLower(int upperWidth, int lowerWidth) {
        return upperWidth >= lowerWidth ? (int)((upperWidth -lowerWidth) * alignment) : 0;
    }
    
    private void relocateBranch(DecisionTreeNode node, int delta) {
        for (DecisionTreeNodeConnection path : node.getOutputs()) {
            DecisionTreeNode child = path.getChild();
            Point loc = child.getLocation();
            loc.x += delta;
            child.setLocation(loc);
            relocateBranch(child, delta);
        }
    }
}
