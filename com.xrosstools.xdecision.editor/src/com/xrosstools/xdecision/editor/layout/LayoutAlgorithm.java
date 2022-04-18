package com.xrosstools.xdecision.editor.layout;

import org.eclipse.draw2d.geometry.Point;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;

public class LayoutAlgorithm {
    private int charWidth = 10;
    private int margin  = 100;
    private int minWidth = 100;
    private int horizantalSpace;
    private int verticalSpace;
    private int nodeHeight;
    private float alignment;
    
    boolean useNew = true;
    
	public void layout(DecisionTreeDiagram diagram){
	    horizantalSpace = diagram.isHorizantal() ? diagram.getVerticalSpace() : diagram.getHorizantalSpace();
	    verticalSpace = diagram.isHorizantal() ? diagram.getHorizantalSpace() : diagram.getVerticalSpace();
	    
        nodeHeight = diagram.isHorizantal() ? diagram.getNodeWidth() : diagram.getNodeHeight();
        
        alignment = diagram.getAlignment();

		for(DecisionTreeNode node: diagram.getRoots()) {
            layout(margin, node, 0);
		}
	}

    private int layout(int leftPos, DecisionTreeNode node, int depth){
        int width = getNodeWidth(node);

        int branchWidth = 0;
        int nextLeftPos = leftPos;
        for(DecisionTreeNodeConnection path : node.getOutputs()){
            branchWidth += layout(nextLeftPos + branchWidth, path.getChild(), depth + 1) + horizantalSpace;
        }
        
        branchWidth = branchWidth == 0 ? 0 : branchWidth - horizantalSpace;
        
        int x = leftPos + (branchWidth == 0 ? 0 : (int)((branchWidth - width) * alignment));
        int y = margin + (depth) * (verticalSpace + nodeHeight);
        
        node.setLocation(new Point(x, y));

        return Math.max(width, branchWidth) + getConnWidth(node.getInput());
    }
    
    private int getNodeWidth(DecisionTreeNode node) {
        if(node.getActualWidth() > 0)
            return node.getActualWidth();

        int width = node.getNodeExpression() == null ? 0 : node.getNodeExpression().toString().length();
        
        width *= charWidth;// average char width
        width += 10;// gap
        
        return Math.max(width, minWidth);
    }

    private int getConnWidth(DecisionTreeNodeConnection conn) {
        if(conn == null)
            return 0;

        if(conn.getActualWidth() > 0)
            return conn.getActualWidth();
        
        int conditionCharCount = 0;
        
        conditionCharCount = conn.getOperator() == null ? 0 : conn.getOperator().toString().length();
        conditionCharCount += conn.getExpression() == null ? 0 : conn.getExpression().toString().length();
        
        return conditionCharCount * charWidth / 2;
    }
}
