package com.xrosstools.xdecision.editor.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

public class DecisionTreeNodeFigure extends RoundedRectangle {
    private Label decisionLabel;
    private IFigure nodeExpression;
    private BorderLayout layout= new BorderLayout();

    public DecisionTreeNodeFigure() {
    	layout= new BorderLayout();
    	setLayoutManager(layout);
    	this.setBorder(new MarginBorder(5));
    	
    	//To make sure the minimal width of the node figure 
    	Figure  widthLine = new Figure();
        widthLine.setPreferredSize(new Dimension(90, 1));
        add(widthLine);
        layout.setConstraint(widthLine, PositionConstants.TOP);
        
        decisionLabel = new Label();
        decisionLabel.setLabelAlignment(PositionConstants.CENTER);
        decisionLabel.setForegroundColor(ColorConstants.black);
        add(decisionLabel);
        layout.setConstraint(decisionLabel, PositionConstants.CENTER);
    }

    public Rectangle getTextBounds() {
        return nodeExpression.getBounds();
    }

    public void setDecision(String decision) {
        decisionLabel.setText(decision);
        repaint();
    }
    
    public void setExpressionFigure(IFigure nodeExpression) {
        this.nodeExpression = nodeExpression;
        add(nodeExpression);
        layout.setConstraint(nodeExpression, PositionConstants.BOTTOM);
    	repaint();
    }
}
