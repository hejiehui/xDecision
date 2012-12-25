package com.ebay.tools.decisiontree.editor.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Rectangle;

public class DecisionTreeNodeFigure extends RoundedRectangle {
    private Label factorLabel;
    private Label decisionLabel;

    public DecisionTreeNodeFigure() {
    	BorderLayout layout= new BorderLayout();
    	setLayoutManager(layout);
    	this.setBorder(new MarginBorder(5));
    	
    	factorLabel = new Label();
        factorLabel.setLabelAlignment(PositionConstants.CENTER);
        factorLabel.setForegroundColor(ColorConstants.darkGreen);
        add(factorLabel);
    	layout.setConstraint(factorLabel, PositionConstants.BOTTOM);
    	
    	decisionLabel = new Label();
    	decisionLabel.setLabelAlignment(PositionConstants.CENTER);
    	decisionLabel.setForegroundColor(ColorConstants.black);
        add(decisionLabel);
    	layout.setConstraint(decisionLabel, PositionConstants.CENTER);
    }

    public Rectangle getTextBounds() {
        return factorLabel.getTextBounds();
    }

    public void setFactor(String name) {
    	factorLabel.setText(name);
    	factorLabel.setToolTip(new Label(name));
    	repaint();
    }
    
    public void setDecision(String decision) {
    	decisionLabel.setText(decision);
    	decisionLabel.setToolTip(new Label(decision));
        repaint();
    }
}
